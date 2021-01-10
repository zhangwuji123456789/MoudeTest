package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.Setmeal;

import java.util.List;

/**
 * @Author Ma zhi lin
 * @Date 2021/1/8 21:09
 * @Version 1.0
 */
public interface SetmealService {
    /*
    * 添加套餐
    * */
    void add(Setmeal setmeal, Integer[] checkgroupIds);

    /*
    * 分页查询
    * */

    PageResult<Setmeal> findPage(QueryPageBean queryPageBean);


    /*
    * 通过ID查询套餐的信息
    * */
    Setmeal findById(int id);

    /*
    * 通过id查询选中的检查组ids
    * */
    List<Integer> findCheckGroupIdsBySetmealId(int id);

    /*
     * 更新业务
     * */
    void update(Setmeal setmeal, Integer[] checkgroupIds);


    /*
    * 根据ID删除套餐
    * */
    void deleteById(int id);
}
