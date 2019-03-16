package org.seckill.service;

import org.seckill.dto.Exposer;
import org.seckill.dto.SecKillExecution;
import org.seckill.entity.SecKill;
import org.seckill.exception.InsertException;
import org.seckill.exception.SecKillCloseException;
import org.seckill.exception.SecKillException;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface SecKillService {
    List<SecKill> getSecKillList();
    void addItem(SecKill secKill);
    SecKill getSecKillById(long secKillId);
    Exposer exportSecKillUrl(long secKillId);
    SecKillExecution executeSecKill(long secKillId, String phone, String md5)
            throws SecKillException, SecKillCloseException, InsertException;
    SecKillExecution executeSecKillUseP(long secKillId, String phone, String md5);
 }
