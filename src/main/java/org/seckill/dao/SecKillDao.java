package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.SecKill;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface SecKillDao {
    Integer reduceQuantity(@Param("secKillId") long secKillId, @Param("killTime") Date killTime);
    SecKill queryById(long secKillId);
    List<SecKill> queryAll(@Param("offset") int offset, @Param("limit")int limit);
    int addItem(SecKill secKill);
    void executionSeckill(Map map);
}
