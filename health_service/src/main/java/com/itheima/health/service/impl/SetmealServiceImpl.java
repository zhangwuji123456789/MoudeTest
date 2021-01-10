package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.container.page.PageHandler;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.StringUtil;
import com.itheima.health.dao.SetmealDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author Ma zhi lin
 * @Date 2021/1/8 21:45
 * @Version 1.0
 */
@Service(interfaceClass = SetmealService.class)
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealDao setmealDao;


    @Override
    @Transactional
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        //先添加套餐
        setmealDao.add(setmeal);
        //获取套餐的id
        Integer id = setmeal.getId();
        //遍历检查项ID数组
        if (null != checkgroupIds) {
            //添加套餐和检查组的关系
            for (Integer checkgroupId : checkgroupIds) {
                setmealDao.addSetmealCheckGroup(id,checkgroupId);
            }
        }


    }
    /*
    * 分页查询
    * */

    @Override
    public PageResult<Setmeal> findPage(QueryPageBean queryPageBean) {
        //首先获取当前页面，并限制页面的大小 并用分页工具 不要用阿里
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        //查询条件 和模糊查询
        if (!StringUtil.isEmpty(queryPageBean.getQueryString())) {
            //模糊查询
            queryPageBean.setQueryString("%"+ queryPageBean.getQueryString()+"%");
        }
        //条件查询，将获取的条件放进来 进行查询并分页
        Page<Setmeal> page = setmealDao.findByCondition(queryPageBean.getQueryString());
        //放进去总记录数 和
        return new PageResult<Setmeal>(page.getTotal(),page.getResult());

    }

    /*
    * 通过ID查询套餐的信息
    * */
    @Override
    public Setmeal findById(int id) {
        //直接调用Dao方法调用，然后返回
        return setmealDao.findById(id);
    }

    /*
    * 通过ID查询选中的检查组的IDS
    * */
    @Override
    public List<Integer> findCheckGroupIdsBySetmealId(int id) {
        return setmealDao.findCheckGroupIdsBySetmealId(id);
    }


    /*
     * 更新套餐
     * 1.要更新套餐信息
     * 2.删除旧关系
     * 3.添加新关系 并进行判断
     * */
    @Override
    public void update(Setmeal setmeal, Integer[] checkgroupIds) {
        setmealDao.update(setmeal);
        setmealDao.deleteSetmealCheckGroup(setmeal.getId());
        //添加新关系
        if (null != checkgroupIds){
            for (Integer checkgroupId : checkgroupIds) {
                setmealDao.addSetmealCheckGroup(setmeal.getId(),checkgroupId);
            }
        }


    }

    /*
    * 根据ID删除套餐
    * 1.判断此订单是否被使用
    * 2.使用了的话要报异常不能删除
    * 3.没有使用先删除套餐和检查组绑定关系
    * 4.在删除套餐
    * */
    @Override
    public void deleteById(int id) {
        //判断订单是否被使用
        int count = setmealDao.findCountBySetmealId(id);
        //做判断如果使用则报异常
        if (count>0) {
            try {
                throw new  Exception("该订单已被使用，不能删除");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //没有使用先删除套餐和检查组绑定关系
        setmealDao.deleteSetmealCheckGroup(id);
        //在删除套餐
        setmealDao.deleteById(id);

    }
}
