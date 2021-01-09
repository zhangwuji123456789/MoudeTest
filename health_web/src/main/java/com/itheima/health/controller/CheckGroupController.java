package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.service.CheckItemService;
import com.itheima.health.service.CherkGroupService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author Ma zhi lin
 * @Date 2021/1/7 18:43
 * @Version 1.0
 */
@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {

    /*
    * 添加检查项
    * 获取检查项数据和检查项的ID
    * */

    @Reference
    private CherkGroupService cherkGroupService;
    @RequestMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup, Integer[] checkitemIds){
        cherkGroupService.add(checkGroup,checkitemIds);
        return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }


    /*
     * 检查项分页
     * */
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult<CheckGroup> pageResult = cherkGroupService.findPage(queryPageBean);
        return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,pageResult);

    }

    /*
    * 根据ID查询检查组
    * */
    @GetMapping("/findById")
    public Result findById(int id){
       CheckGroup checkGroup = cherkGroupService.findById(id);
       return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroup);
    }

    /**
     * 通过检查组id查询选中的检查项id
     * @param id
     * @return
     */
    @GetMapping("/findCheckItemIdsByCheckGroupId")
    public Result findCheckItemIdsByCheckGroupId(int id){
        // 通过检查组id查询选中的检查项id集合
        List<Integer> checkItemIds = cherkGroupService.findCheckItemIdsByCheckGroupId(id);
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItemIds);
    }

    /*
    * 修改检查项
    * */
    @RequestMapping("/update")
    public Result update(@RequestBody CheckGroup checkGroup, Integer[] checkitemIds){
        cherkGroupService.update(checkGroup,checkitemIds);
        return new Result(true, MessageConstant.EDIT_CHECKGROUP_SUCCESS);
    }

    /*
    * 删除检查组
    * */
    @PostMapping("/deleteById")
    public Result deleteById(int id){
        cherkGroupService.deleteById(id);
        return new Result(true,MessageConstant.DELETE_CHECKGROUP_SUCCESS);
    }

    /**
     *
     *查询所有检查组
     */
    @GetMapping("/findAll")
    public Result findll(){
       List<CheckGroup> list = cherkGroupService.findAll();
       return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,list);

    }


}
