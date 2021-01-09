package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;

import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.SetmealService;
import com.itheima.health.utils.QiNiuUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;



import java.io.IOException;
import java.util.HashMap;

import java.util.Map;
import java.util.UUID;


/**
 * @Author Ma zhi lin
 * @Date 2021/1/8 20:24
 * @Version 1.0
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {
        private static Logger log = LoggerFactory.getLogger(SetmealController.class);

        @Reference
        private SetmealService setmealService;
    /**
     * 上传图片
     */
    @PostMapping("/upload")
    public Result upload(MultipartFile imgFile){
        //1. 获取源文件名
        String originalFilename = imgFile.getOriginalFilename();
        //2. 截取后缀名
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        //3. 生成唯一id,
        String uniqueId = UUID.randomUUID().toString();
        //4. 拼接唯一文件名
        String filename = uniqueId + suffix;
        //5. 调用7牛工具上传图片
        try {
            QiNiuUtils.uploadViaByte(imgFile.getBytes(), filename);

            //6.构建返回的数据
          /*
                domain: 七牛的域名 图片回显imageUrl = domain+图片名
             */
            Map<String, String> map = new HashMap<String, String>(2);
            map.put("imgName",filename);
            map.put("domain",QiNiuUtils.DOMAIN);

            //7.封装到result里面去，在返回给页面
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS,map);
        } catch (IOException e) {
            log.error("上传文件失败了",e);
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
    }


    /*
    *
    * 添加套餐
    * */
    @PostMapping("/add")
    public Result add(@RequestBody Setmeal setmeal,Integer[] checkgroupIds){
        //调用添加服务套餐
        setmealService.add(setmeal,checkgroupIds);
        return new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);

    }

    /*
    * 套餐管理分页
    * */
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        //调用套餐管理分页service
        PageResult<Setmeal> setmealPageResult = setmealService.findPage(queryPageBean);
        return new Result(true,MessageConstant.QUERY_SETMEALLIST_SUCCESS,setmealPageResult);
    }





}
