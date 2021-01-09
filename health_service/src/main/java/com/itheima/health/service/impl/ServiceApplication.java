package com.itheima.health.service.impl;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @Author Ma zhi lin
 * @Date 2021/1/5 18:15
 * @Version 1.0
 */
public class ServiceApplication {
    public static void main(String[] args) throws IOException {
        new ClassPathXmlApplicationContext("classpath:spring-service.xml");
        System.in.read();
    }
}
