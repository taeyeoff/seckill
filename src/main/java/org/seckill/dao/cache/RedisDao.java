package org.seckill.dao.cache;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import org.seckill.entity.SecKill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
@Repository
public class RedisDao {
    private Logger logger= LoggerFactory.getLogger(this.getClass());
    @Autowired
    private JedisPool jedisPool;
    private RuntimeSchema<SecKill> schema=RuntimeSchema.createFrom(SecKill.class);
    public SecKill getSecKill(long seckillId){
        try{
            Jedis jedis=jedisPool.getResource();
            try{
                String key="seckill:"+seckillId;
                byte[] bytes=jedis.get(key.getBytes());
                if(bytes!=null){
                    SecKill secKill=schema.newMessage();
                    ProtobufIOUtil.mergeFrom(bytes,secKill,schema);
                    return secKill;
                }
            }finally{
                jedis.close();
            }
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return null;
    }
    public String putSecKill(SecKill secKill){
        try{
            Jedis jedis=jedisPool.getResource();
            try{
                String key="seckill:"+secKill.getSeckillId();
                byte[] bytes=ProtobufIOUtil.toByteArray(secKill,schema,
                        LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
                int timeout=60*60;//1小时后失效
                String result=jedis.setex(key.getBytes(),timeout,bytes);
                return result;
            }catch(Exception e){
                logger.error(e.getMessage(), e);
            }finally{
                jedis.close();
            }
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }
}
