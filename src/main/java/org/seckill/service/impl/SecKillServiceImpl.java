package org.seckill.service.impl;

import org.apache.commons.collections4.MapUtils;
import org.seckill.dao.SecKillDao;
import org.seckill.dao.SuccessKilledDao;
import org.seckill.dao.cache.RedisDao;
import org.seckill.dto.Exposer;
import org.seckill.dto.SecKillExecution;
import org.seckill.entity.SecKill;
import org.seckill.entity.SuccessSecKilled;
import org.seckill.enums.SecKillStateEnum;
import org.seckill.exception.InsertException;
import org.seckill.exception.SecKillCloseException;
import org.seckill.exception.SecKillException;
import org.seckill.service.SecKillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SecKillServiceImpl implements SecKillService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private String salt = "kfh86fd5h45t1';l'';'l[;l[-pl[pplp-[pl[pplp-0";
    @Autowired
    private SecKillDao secKillDao;
    @Autowired
    private SuccessKilledDao successKilledDao;
    @Autowired
    private RedisDao redisDao;

    @Override
    public List<SecKill> getSecKillList() {
        return secKillDao.queryAll(0, 100);
    }

    @Override
    public void addItem(SecKill secKill) {
       secKillDao.addItem(secKill);
    }

    @Override
    public SecKill getSecKillById(long secKillId) {
        return secKillDao.queryById(secKillId);
    }

    @Override
    public Exposer exportSecKillUrl(long secKillId) {
        SecKill secKill = redisDao.getSecKill(secKillId);
        if (secKill == null) {
            secKill = secKillDao.queryById(secKillId);
            if (secKill == null) {
                return new Exposer(false, secKillId);
            } else {
                System.out.println(redisDao.putSecKill(secKill));
            }
        }
        Date startTime = secKill.getStartTime();
        Date endTime = secKill.getEndTime();
        Date nowTime = new Date();
        if (nowTime.getTime() < startTime.getTime() || nowTime.getTime() > endTime.getTime()) {
            return new Exposer(false, secKillId, startTime.getTime(),
                    endTime.getTime(), nowTime.getTime());
        }
        String md5 = getMD5(secKillId);
        return new Exposer(true, md5, secKillId);
    }

    private String getMD5(long secKillId) {
        String base = secKillId + "/" + salt;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    @Override
    @Transactional
    public SecKillExecution executeSecKill(long secKillId, String phone, String md5) throws SecKillException, SecKillCloseException, InsertException {
        if (md5 == null || !getMD5(secKillId).equals(md5)) {
            throw new SecKillException("secKill data rewrite");
        }
        Date nowTime = new Date();
        try {
            int insertCount = 0;
            try {
                insertCount = successKilledDao.insertSuccessKilled(secKillId, phone);
            } catch (Exception e) {
                throw new InsertException("insert error");
            }
            if (insertCount <= 0) {
                throw new InsertException("insert error");
            } else {
                int updateCount = secKillDao.reduceQuantity(secKillId, nowTime);
                if (updateCount <= 0) {
                    throw new SecKillCloseException("secKill is closed");
                } else {
                    SuccessSecKilled successSecKilled = successKilledDao.queryByIdWithSecKill(secKillId, phone);
                    return new SecKillExecution(secKillId, SecKillStateEnum.SUCCESS, successSecKilled);
                }
            }
        } catch (SecKillCloseException e1) {
            throw e1;
        } catch (InsertException e2) {
            throw e2;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SecKillException("secKill inner error:" + e.getMessage());
        }

    }
    @Override
    public SecKillExecution executeSecKillUseP(long secKillId, String phone, String md5) {
        if (md5 == null || !getMD5(secKillId).equals(md5)) {
            throw new SecKillException("secKill data rewrite");
        }
        Date nowTime = new Date();
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("id",secKillId);
        map.put("phone",phone);
        map.put("killTime",nowTime);
        map.put("result",null);
        secKillDao.executionSeckill(map);
        Integer res=MapUtils.getInteger(map,"result",100);
        if(res==1){
            SuccessSecKilled successSecKilled=successKilledDao.queryByIdWithSecKill(secKillId,phone);
            return new SecKillExecution(secKillId,SecKillStateEnum.SUCCESS,successSecKilled);
        }else {
            return new SecKillExecution(secKillId,SecKillStateEnum.stateOf(res));
        }
    }
}
