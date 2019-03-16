package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.SuccessSecKilled;

public interface SuccessKilledDao {
    Integer insertSuccessKilled(@Param("secKillId")long secKillId, @Param("phone")String phone);
    SuccessSecKilled queryByIdWithSecKill(@Param("secKillId") long secKillId,@Param("phone") String phone);
}
