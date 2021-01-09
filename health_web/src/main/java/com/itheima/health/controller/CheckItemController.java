package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.service.CheckItemService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author Ma zhi lin
 * @Date 2021/1/5 21:46
 * @Version 1.0
 *
 * Json 要用rescontroller这个注解
 *
 */

@RestController
@RequestMapping("/checkitem")
public class CheckItemController {

    /*
    * 订阅检查服务
    * reference 是调用service服务，注册到dubbo
    * 要用阿里的注解
    * */
    @Reference
    private CheckItemService checkItemService;
    @GetMapping("/findAll")
    public Result findAll(){
        List<CheckItem> list = checkItemService.findAll();
        //封装到Result里面在返回
        return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS,list);
    }


    /*
    *
    * 添加检查项
    * */

    @PostMapping("/add")
    public Result add(@RequestBody CheckItem checkItem){
        //1.调用service执行方法添加checkItem
        checkItemService.add(checkItem);
        return new Result(true,MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }


    /*
    * 检查项分页
    * */
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {
       PageResult<CheckItem> pageResult =  checkItemService.findPage(queryPageBean);
       return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,pageResult);

    }

}
