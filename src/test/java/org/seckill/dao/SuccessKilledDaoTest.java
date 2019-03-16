package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.SuccessSecKilled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKilledDaoTest {
    @Autowired
    private SuccessKilledDao dao;
    @Test
    public void insertSuccessKilled() {
        System.out.println(dao);
        int insertCount=dao.insertSuccessKilled(10021,"1234567890");
        System.out.println(insertCount);
    }

    @Test
    public void queryByIdWithSecKill() {
        SuccessSecKilled success=dao.queryByIdWithSecKill(1021L,"124567890");
        System.out.println(success);
        System.out.println(success.getSecKill());
    }
}