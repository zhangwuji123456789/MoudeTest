package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;

import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.SetmealService;
import com.itheima.health.utils.QiNiuUtils;

import org.omg.CORBA.PUBLIC_MEMBER;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;



import java.io.IOException;
import java.util.HashMap;

import java.util.List;
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


    /*
    *
    * 1. 回显
   * 【编辑】绑定事件，重置表单，通过id查询套餐（除了返回套餐信息，还要返回domain)，绑定套餐信息到formData, 回显图片imageUrl=domain+formData.img。
   * 查询所有检查组列表
   * 通过套餐id查询选中的检查组id集合，绑定到checkgroupIds里

2. 提交 与添加类似

   * 【确定】编辑窗口绑定事件，提交更新套餐的请求，提交formData, checkgroupIds选中的检查组id集合，响应结果。提示结果，如果成功则关闭编辑窗口，刷新列表数据
   * SetmealController提供update的方法，使用Setmeal对象来接收formData，使用Integer数组来接收checkgroupIds，调用服务更新套餐，响应结果给页面
   * 在服务SetmealService处理
     * 先更新套餐
     * 删除旧关系
     * 遍历检查组id数组，添加新关系
     * 事务控制
   * SetmealDao处理
     * 更新套餐 t_setmeal
     * 删除套餐与检查组关系 t_setmeal_checkgroup
    *
    *
    *通过ID查询套餐的信息
    * */
    @GetMapping("/findById")
    public Result findById(int id){
        //service服务进行查询
        Setmeal setmeal = setmealService.findById(id);
        //前端显示图片要全路径
        //封装到Map中，解决图片路径问题 obj 注意下  添加fromdata， 调用七牛工具解决路径问题
        Map<String, Object> imgMap = new HashMap<String, Object>();
        imgMap.put("setmeal",setmeal);
        imgMap.put("domain",QiNiuUtils.DOMAIN);
        return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,imgMap);
    }

    /*
    *
    * 通过ID查询检查组的ID
    * */
    @GetMapping("findCheckGroupIdsBySetmealId")
    public Result findCheckGroupIdsBySetmealId(int id){
        //调用service方法然后用list数组的方式来接收 因为是ID所以要用int类型
        List<Integer> checkgroupIds = setmealService.findCheckGroupIdsBySetmealId(id);
        return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkgroupIds);
    }

    /*
    * 修改套餐
    * */
    @PostMapping("/update")
    public Result update(@RequestBody Setmeal setmeal ,Integer[] checkgroupIds){
        setmealService.update(setmeal,checkgroupIds);
        return new Result(true,MessageConstant.EDIT_SETMEAL_SUCCESS);
    }
    /*
    * 跟据ID删除套餐列表
    * */
   @PostMapping("/deleteById")
    public Result deleteById(int id){
       //调用删除方法
       setmealService.deleteById(id);
       return new Result(true,MessageConstant.DELETE_Setmeal_SUCCESS);
   }

}
