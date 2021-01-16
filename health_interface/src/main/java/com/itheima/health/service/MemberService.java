package com.itheima.health.service;

import com.itheima.health.pojo.Member;

/**
 * @Author Ma zhi lin
 * @Date 2021/1/16 18:21
 * @Version 1.0
 */
public interface MemberService {
    /**
     * 查询是否为会员
     * @param telephone
     * @return
     */
    Member findByTelephone(String telephone);


    /**
     * 添加会员
     * @param member
     */
    void add(Member member);
}
