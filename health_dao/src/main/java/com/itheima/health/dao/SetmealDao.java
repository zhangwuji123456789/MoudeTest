package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.Setmeal;
import org.apache.ibatis.annotations.Param;

/**
 * @Author Ma zhi lin
 * @Date 2021/1/8 21:47
 * @Version 1.0
 */
public interface SetmealDao {

    /*
    * 添加套餐
    * */
    void add(Setmeal setmeal);



    /*
    *添加套餐和检查组关系
    * */
    void addSetmealCheckGroup(@Param("id") Integer id, @Param("checkgroupId") Integer checkgroupIds);

    /*
    * 分页查询
    * */
    Page<Setmeal> findByCondition(String queryString);
}
