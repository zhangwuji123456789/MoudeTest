package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.OrderSetting;
import com.itheima.health.service.OrderSettingService;
import com.itheima.health.utils.POIUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author Ma zhi lin
 * @Date 2021/1/10 19:13
 * @Version 1.0
 */
@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {
    @Reference
    private OrderSettingService orderSettingService;

    /**
     * 根据表格添加数据
     * @param excelFile
     * @return
     */
    @PostMapping("/upload")
    public Result upload(MultipartFile excelFile){

        try {
            List<String[]> list = POIUtils.readExcel(excelFile);
            SimpleDateFormat sfd = new SimpleDateFormat(POIUtils.DATE_FORMAT);
            List<OrderSetting> collect = list.stream().map(res -> {
                OrderSetting orderSetting = new OrderSetting();
                try {
                    //0是日期 1.是预约数
                    orderSetting.setOrderDate(sfd.parse(res[0]));
                    orderSetting.setNumber(Integer.valueOf(res[1]));

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return orderSetting;
                //这叫个工厂类
            }).collect(Collectors.toList());
            orderSettingService.upload(collect);
            return new Result(true, MessageConstant.UPLOAD_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
    }

    /**
     * 通过月份展示预约信息
     * @param mouth
     * @return
     */
    @GetMapping("/getOrderSettingByMonth")
    public Result getOrderSettingByMonth(String mouth){
       List<Map> list = orderSettingService.findByMouth(mouth);
       return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,list);
    }

    /**
     * 根据参数修改值
     * @param orderSetting
     * @return
     */
    @PostMapping("/editNumberByDate")
    public Result editNumberByDate(@RequestBody OrderSetting orderSetting){
      orderSettingService.update(orderSetting);
      return new Result(true,"修改成功");
    }
}
