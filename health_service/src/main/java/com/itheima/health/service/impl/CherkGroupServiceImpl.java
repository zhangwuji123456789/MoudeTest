package com.itheima.health.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.dao.CheckGroupDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.CheckGroup;

import com.itheima.health.service.CherkGroupService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.List;

/**
 * @Author Ma zhi lin
 * @Date 2021/1/7 18:57
 * @Version 1.0
 */
@Service(interfaceClass = CherkGroupService.class)
public class CherkGroupServiceImpl implements CherkGroupService {

    @Autowired
    private CheckGroupDao checkGroupDao;

    /*
    * 添加检查项
    * 1.检查组的数据
    * 2.是Id
    * */
    @Override
    @Transactional
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
        //添加检查组
        checkGroupDao.add(checkGroup);
        //获取检查项的Id
        Integer checkGroupId = checkGroup.getId();
        //遍历选中的检查项ID和数组
        if (null != checkitemIds) {
            //这里不太懂 要问一下
            for (Integer checkitemId :checkitemIds){
                //添加检查组于检查项的关系
                checkGroupDao.addCheckGroupCheckItem(checkGroupId,checkitemId);
            }

        }


    }


    /*
    * 检查项分页
    * */
    @Override
    public PageResult<CheckGroup> findPage(QueryPageBean queryPageBean) {
        //  currentpage 是关于    pageSize是限制页码大小的api 在实际开发过程中需要限制页数的大小
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        // 条件查询
        if (StringUtils.isNotEmpty(queryPageBean.getQueryString())) {
            // 有查询条件， 模糊查询
            queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");
        }
        //调用Dao方法执行 并用page进行接收 不用list 因为page继承了接口 源码里面total result
        Page<CheckGroup> page = checkGroupDao.findByCondition(queryPageBean.getQueryString());
        PageResult<CheckGroup> pageResult = new PageResult<CheckGroup>(page.getTotal(), page.getResult());
        return pageResult;
    }



    /*
    * 通过ID查询检查组
    * */
    @Override
    public CheckGroup findById(int id) {
        return  checkGroupDao.findById(id);

    }


    /*
    * 通过检查组id查询选中的检查项id
    * */
    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(int id) {

        return checkGroupDao.findCheckItemIdsByCheckGroupId(id) ;
    }

    /*
     * 修改检查项
     * 需要在看一遍流程
     * */
    @Override
    public void update(CheckGroup checkGroup, Integer[] checkitemIds) {
        //更新检查组
        checkGroupDao.update(checkGroup);
        //先删除旧的关系
        checkGroupDao.deleteCheckGroupCheckItem(checkGroup.getId());
        //遍历选中的检查项的ID数组
        if (null != checkGroup) {
            //遍历数组
            for (Integer checkitemId : checkitemIds) {
                //添加检查组和检查项的关系
                checkGroupDao.addCheckGroupCheckItem(checkGroup.getId(),checkitemId);
            }
        }

    }


    /*
    *
    *
    * 删除检查组
    * 添加事务
    *
    * 检查组是checkgroup
    * 检查项是checkItem
    *
    * //要改为自定义的异常Myex
    * */
    @Override
    @Transactional
    public void deleteById(int id)  {
       //不能直接删除，需要判断当前检查组是否和检查项、套餐关联，如果已经和检查项、套餐进行了关联则不允许删除
        //判断当前检查组是否被使用了
       int count =  checkGroupDao.findCountByCheckGroupId(id);
       if (count > 0 ){
           //就证明当前检查组已经被使用了，就报异常信息
           try {
               throw new Exception("该检查组已被使用，无法删除");
           } catch (Exception e) {
               e.printStackTrace();
           }
       }
       //删除检查组和检查项的关系
       checkGroupDao.deleteCheckGroupCheckItem(id);
       //根据ID删除检查组的数据
        checkGroupDao.deleteById(id);


    }

    @Override
    public List<CheckGroup> findAll() {
        return checkGroupDao.findAll();
    }
}
