package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Setmeal;

import com.itheima.health.service.SetmealService;
import com.itheima.health.utils.QiNiuUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

/**
 * @Author Ma zhi lin
 * @Date 2021/1/11 16:30
 * @Version 1.0
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealMobileController {
    @Reference
    private SetmealService setmealService;

    /**
     * 查询所有套餐
     *
     * @return
     */
    @GetMapping("/getSetmeal")
    public Result getSetmeal() {
        List<Setmeal> list = setmealService.findAll();
        //使用流的方式进行拼接七牛路径和图片
        list.stream().forEach(res -> {
            res.setImg(QiNiuUtils.DOMAIN + res.getImg());
        });
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, list);
    }

    @GetMapping("/findDetailById")
    public Result findDetailById(int id) {
        Setmeal setmeal = setmealService.findDetailById(id);
        //拼接图片
        setmeal.setImg(QiNiuUtils.DOMAIN + setmeal.getImg());
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, setmeal);

    }
}