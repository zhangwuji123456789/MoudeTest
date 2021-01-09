package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.CheckGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author Ma zhi lin
 * @Date 2021/1/7 19:10
 * @Version 1.0
 */
public interface CheckGroupDao {
    /*
    * 添加检查组
    * */
    void add(CheckGroup checkGroup);




    /*
    * 添加检查项
    * */
    void addCheckGroupCheckItem(@Param("checkgroupId") Integer checkGroupId, @Param("checkitemId") Integer checkitemId);

    /*
    * 检查项分页查询
    * */

    Page<CheckGroup> findByCondition(String queryString);


    /*
    * 通过ID查询检查组
    * */
    CheckGroup findById(int id);


    /*
    * 通过检查组id查询选中的检查项id
    * */
    List<Integer> findCheckItemIdsByCheckGroupId(int id);

    /*
    * 修改检查项
    * */

    void update(CheckGroup checkGroup);

    /*
    *
    * 删除旧关系
    * */

    void deleteCheckGroupCheckItem(Integer id);

    /*
    *判断当前检查组是否被使用了的个数  通过ID查询
    * */
    int findCountByCheckGroupId(int id);

    /*
    * 删除检查组的数据
    * */
    void deleteById(int id);
    /*
    * 查询检查组
    * */
    List<CheckGroup> findAll();
}
