package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.exception.MyException;
import com.itheima.health.pojo.CheckGroup;
import java.util.List;

/**
 * @Author Ma zhi lin
 * @Date 2021/1/7 18:53
 * @Version 1.0
 */
public interface CherkGroupService {




    /*
    * 添加检查项
    * */
    void add(CheckGroup checkGroup, Integer[] checkitemIds);

    PageResult<CheckGroup> findPage(QueryPageBean queryPageBean);



    /*
    * 通过ID查询检查组
    * */
    CheckGroup findById(int id);


    /*
    *
    * 通过检查组id查询选中的检查项id
    * */
    List<Integer> findCheckItemIdsByCheckGroupId(int id);


    /*
     * 修改检查项
     * */
    void update(CheckGroup checkGroup, Integer[] checkitemIds);



    /*
    * 删除检查组
    *
    * 在接口中抛出异常
    * 异常自己定义
    * */
    void deleteById(int id);

    /*
    * 查询检查组
    * */
    List<CheckGroup> findAll();

}
