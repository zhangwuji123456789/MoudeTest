package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.pojo.CheckItem;

import java.util.List;

/**
 * @Author Ma zhi lin
 * @Date 2021/1/5 21:58
 * @Version 1.0
 */
public interface CheckItemService {


    /*
    *
    * 查询所有
    * */

    List<CheckItem> findAll();


    /*
    * 添加检查项
    * */
    void add(CheckItem checkItem);



    /*
     * 检查项分页
     * */
    PageResult findPage(QueryPageBean queryPageBean);

    /*
    * 查询检查组
    * */

    
}
