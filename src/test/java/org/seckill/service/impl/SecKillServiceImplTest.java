package org.seckill.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SecKillExecution;
import org.seckill.entity.SecKill;
import org.seckill.exception.InsertException;
import org.seckill.service.SecKillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml",
                      "classpath:spring/spring-service.xml",
                       "classpath:spring/spring-redis.xml"})
public class SecKillServiceImplTest {
    private final Logger logger= LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SecKillService secKillService;
    @Test
    public void getSecKillList() {
        List<SecKill> list=secKillService.getSecKillList();
        logger.info("list={}",list);
    }

    @Test
    public void getSecKillById() {
        SecKill secKill=secKillService.getSecKillById(1021L);
        logger.info("secKill={}",secKill);
    }
/*
exposer=Exposer{exposed=true, md5='7841cd747942d5c6f3c00507695effa3',
secKillId=1060, start=0, end=0, now=0}
 */
    @Test
    public void exportSecKillUrl() {
        Exposer exposer=secKillService.exportSecKillUrl(1060L);
        logger.info("exposer={}",exposer);
    }

    @Test
    public void executeSecKill() {
        try {
            SecKillExecution secKillExecution=secKillService.executeSecKill(1060L,
                    "123090974","7841cd747942d5c6f3c00507695effa3");
            logger.info("secKillExecution={}",secKillExecution);
        }catch(InsertException e){
            logger.info("插入错误,联合主键冲突");
        }


    }

    @Test
    public void executeSecKillUseP() {
        secKillService.executeSecKillUseP(1113L,"8",
                "5404c8df73833ab8f93484d58630d481");

    }
}