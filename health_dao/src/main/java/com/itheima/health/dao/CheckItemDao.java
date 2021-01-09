package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.CheckItem;

import java.util.List;

/**
 * @Author Ma zhi lin
 * @Date 2021/1/5 22:09
 * @Version 1.0
 */
public interface CheckItemDao {

    /*
    *
    * 查询所有检查项
    *
    * */
    List<CheckItem> findAll();

    /*
    *
    * 添加检查项
    * */
    void add(CheckItem checkItem);


    Page<CheckItem> findByCondition(String queryString);
}
