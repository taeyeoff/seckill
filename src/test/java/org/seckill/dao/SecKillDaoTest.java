package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.SecKill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SecKillDaoTest {
    @Autowired
    private SecKillDao secKillDao;
    @Test
    public void reduceQuantity() {
        Date killTime=new Date();
        int updateCount=secKillDao.reduceQuantity(1040L,killTime);
        System.out.println(updateCount);
    }

    @Test
    public void queryById() {
        long id=1020L;
        SecKill secKill=secKillDao.queryById(id);
        System.out.println(secKill);
    }

    @Test
    public void queryAll() {
        List<SecKill> list=secKillDao.queryAll(1,2);
        for(SecKill a:list){
            System.out.println(a);
        }
    }
}