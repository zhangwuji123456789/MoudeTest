package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.Setmeal;

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
}
