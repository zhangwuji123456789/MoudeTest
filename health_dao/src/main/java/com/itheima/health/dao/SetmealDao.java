package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.Setmeal;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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


    /*
    * 通过ID查询套餐的信息
    * */
    Setmeal findById(int id);

    List<Integer> findCheckGroupIdsBySetmealId(int id);

    /*
     * 通过ID查询检查组的IDs
     * */
    /*
     * 更新
     * */
    void update(Setmeal setmeal);

    /*
     * 删除旧关系
     * */
    void deleteSetmealCheckGroup(Integer id);

    /*
    * 判断订单是否被占用
    * */
    int findCountBySetmealId(int id);

    /*
    * 根据ID删除套餐
    * */
    void deleteById(int id);
}
