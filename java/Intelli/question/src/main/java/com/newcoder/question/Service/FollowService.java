package com.newcoder.question.Service;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.newcoder.question.util.JedisAdapter;
import com.newcoder.question.util.RedisKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class FollowService {
    public static final Logger logger = LoggerFactory.getLogger(FollowService.class);

    @Autowired
    JedisAdapter jedisAdapter;

    /**
     * 用户关注实体
     * */
    public boolean follow(int userId, int entityType, int entityId ){
        try{
            String followerKey = RedisKeyUtil.getFollowerKey(entityType,entityId);
            double score=new Date().getTime();//分数为时间
            //jedisAdapter.zadd(followerKey,score,String.valueOf(userId));

            String followeeKey = RedisKeyUtil.getFolloweeKey(userId,entityType);
            //jedisAdapter.zadd(followeeKey,score,String.valueOf(entityId));

            //使用事务，要么同时成功要么同时失败
            Jedis jedis = jedisAdapter.getJedis();
            Transaction tx = jedisAdapter.multi(jedis);
            tx.zadd(followerKey,score,String.valueOf(userId));
            tx.zadd(followeeKey,score,String.valueOf(entityId));
            List<Object> objects =  tx.exec();
            tx.close();
            return objects.size()==2 && (long)objects.get(0) > 0  && (long)objects.get(1) > 0;

        }catch (Exception e){
            logger.error("关注失败："+e.getMessage());
            return false;
        }
    }
    /***
     *取消关注
     */
    public boolean unfollow(int userId, int entityType, int entityId ){
        try{
            String followerKey = RedisKeyUtil.getFollowerKey(entityType,entityId);
            String followeeKey = RedisKeyUtil.getFolloweeKey(userId,entityType);

            //使用事务，要么同时成功要么同时失败
            Jedis jedis = jedisAdapter.getJedis();
            Transaction tx = jedisAdapter.multi(jedis);
            tx.zrem(followerKey,String.valueOf(userId));
            tx.zrem(followeeKey,String.valueOf(entityId));
            List<Object> objects =  tx.exec();
            tx.close();
            return objects.size()==2 && (long)objects.get(0) > 0  && (long)objects.get(1) > 0;
        }catch (Exception e){
            logger.error("取消关注失败："+e.getMessage());
            return false;
        }
    }
    public List<Integer> getIdsFromSet(Set<String> idSet){
        List<Integer> idList = new ArrayList<>();
        for(String id:idSet){
            idList.add(Integer.valueOf(id));
        }
        return idList;
    }
    /**
     * 某个实体的粉丝列表，按照最新时间排序
     * */
    public List<Integer> getFollowers(int entityType, int entityId, int count){
        String followerKey = RedisKeyUtil.getFollowerKey(entityType,entityId);
        return getIdsFromSet(jedisAdapter.zrevrange(followerKey,0,count));
    }
    public List<Integer> getFollowers(int entityType, int entityId, int offset, int count){
        String followerKey = RedisKeyUtil.getFollowerKey(entityType,entityId);
        return getIdsFromSet(jedisAdapter.zrevrange(followerKey,offset,offset+count));
    }
    /**
     * 用户关注对象列表
     * */
    public List<Integer> getFollowees(int userId, int entityType, int count){
        String followeeKey = RedisKeyUtil.getFolloweeKey(userId,entityType);
        return getIdsFromSet(jedisAdapter.zrevrange(followeeKey,0,count));
    }
    public List<Integer> getFollowees(int userId, int entityType, int offset, int count){
        String followeeKey = RedisKeyUtil.getFolloweeKey(userId,entityType);
        return getIdsFromSet(jedisAdapter.zrevrange(followeeKey,offset,offset+count));
    }


    /**
     * 某个实体有多少个粉丝
     * */
    public long getFollowersCount(int entityType, int entityId){
        String followerKey = RedisKeyUtil.getFollowerKey(entityType,entityId);
        return jedisAdapter.zcard(followerKey);
    }

    /**
     * 某个用户关注的实体数
     * */
    public long getFolloweeCount(int userId, int entityType){
        String followeeKey = RedisKeyUtil.getFolloweeKey(userId,entityType);
        return jedisAdapter.zcard(followeeKey);
    }

    /**
     * 判断用户是否关注了某个实体
     * */
    public boolean isFollower(int userId, int entityType, int entityId){
        String followerKey = RedisKeyUtil.getFollowerKey(entityType,entityId);
       return jedisAdapter.zscore(followerKey,String.valueOf(userId)) != null;
    }
}
