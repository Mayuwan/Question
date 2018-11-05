package com.newcoder.question.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.newcoder.question.Model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.*;

import java.util.List;
import java.util.Set;

@Service
public class JedisAdapter implements InitializingBean{
    private static final Logger logger = LoggerFactory.getLogger(JedisAdapter.class);
    private JedisPool pool;

    @Override
    public void afterPropertiesSet() throws Exception {
         pool = new JedisPool("redis://localhost:6379/10");
    }
    public static void print(int index, Object object){
        System.out.println(String.format("%d, %s",index,object));
    }

    public Jedis getJedis(){
        return pool.getResource();
    }

    public List<Object> exec(Transaction transaction,Jedis jedis, double min, double max){
        try {
            return transaction.exec();
        }catch (Exception e){
            logger.error("multi失败"+e.getMessage());
            transaction.discard();
        }finally {
            if(transaction!=null){
                try{
                    transaction.close();
                }catch (Exception e){
                    logger.error("出现异常："+e.getMessage());
                }
            }
            if(jedis !=null){
                jedis.close();
            }
        }
        return null;
    }
    public Transaction multi(Jedis jedis){

        try {
            return jedis.multi();
        }catch (Exception e){
            logger.error("multi失败"+e.getMessage());
        }finally {
        }
        return null;
    }

    public long zcount(String key, double min, double max){
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.zcount(key,min,max);
        }catch (Exception e){
            logger.error("zset zcount失败"+e.getMessage());
        }finally {
            if(jedis !=null){
                jedis.close();
            }
        }
        return 0;
    }
    public Double zscore(String key, String member){
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.zscore(key,member);
        }catch (Exception e){
            logger.error("zset zscore失败"+e.getMessage());
        }finally {
            if(jedis !=null){
                jedis.close();
            }
        }
        return null;
    }

    public Set<String> zrevrange(String key, long start, long end){
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.zrevrange(key,start,end);
        }catch (Exception e){
            logger.error("zset zrevrange失败"+e.getMessage());
        }finally {
            if(jedis !=null){
                jedis.close();
            }
        }
        return null;
    }
    public long zcard(String key){
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.zcard(key);
        }catch (Exception e){
            logger.error("zset zcard失败"+e.getMessage());
        }finally {
            if(jedis !=null){
                jedis.close();
            }
        }
        return 0;
    }
    public long zrem(String key,String value){
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.zrem(key,value);
        }catch (Exception e){
            logger.error("zset zrem失败"+e.getMessage());
        }finally {
            if(jedis !=null){
                jedis.close();
            }
        }
        return 0;
    }

        public long zadd(String key,double score,String value){
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.zadd(key,score,value);
        }catch (Exception e){
            logger.error("zset add失败"+e.getMessage());
        }finally {
            if(jedis !=null){
                jedis.close();
            }
        }
        return 0;
    }
    public long lPush(String key,String value){
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.lpush(key,value);
        }catch (Exception e){
            logger.error("list lpush失败"+e.getMessage());
        }finally {
            if(jedis !=null){
                jedis.close();
            }
        }
        return 0;
    }
    public List<String> brpop(int timeout, String key){
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.brpop(timeout,key);
        }catch (Exception e){
            logger.error("list brpop失败"+e.getMessage());
        }finally {
            if(jedis !=null){
                jedis.close();
            }
        }
        return null;
    }

    public long scard(String key){
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.scard(key);
        }catch (Exception e){
            logger.error("set card失败"+e.getMessage());
        }finally {
            if(jedis !=null){
                jedis.close();
            }
        }
        return 0;
    }
    public long sadd(String key, String value){
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.sadd(key,value);
        }catch (Exception e){
            logger.error("set添加失败"+e.getMessage());
        }finally {
            if(jedis !=null){
                jedis.close();
            }
        }
        return 0;
    }
    public long srem(String key, String value){
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.srem(key,value);
        }catch (Exception e){
            logger.error("set删除失败"+e.getMessage());
        }finally {
            if(jedis !=null){
                jedis.close();
            }
        }
        return 0;
    }
    public boolean sismember(String key, String value){
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.sismember(key,value);
        }catch (Exception e){
            logger.error("判断是否在set中失败"+e.getMessage());
        }finally {
            if(jedis !=null){
                jedis.close();
            }
        }
        return false;
    }


    public static void main(String[] args) {
        Jedis jedis = new Jedis("redis://localhost:6379/9");
        jedis.flushDB();

        jedis.set("hello", "jedis");
        jedis.rename("hello", "newHello");
        print(1, jedis.get("newHello"));

        jedis.setex("hello", 10, "java");

        jedis.set("pv", "100");
        jedis.incr("pv");
        print(2, jedis.get("pv"));
        jedis.incrBy("pv", 12);
        jedis.decrBy("pv", 2);
        print(3, jedis.get("pv"));

        String listName = "list";
        jedis.del(listName);
        for (int i = 0; i < 10; i++) {
            jedis.lpush(listName, String.valueOf(i));//先进后出
        }
        print(4, jedis.lrange(listName, 0, 12));
        print(5, jedis.llen(listName));
        print(6, jedis.lpop(listName));
        print(7, jedis.llen(listName));
        print(8, jedis.lrange(listName, 3, 6));
        print(9, jedis.lindex(listName, 4));
        print(10, jedis.linsert(listName, BinaryClient.LIST_POSITION.BEFORE, "4", "vfb"));
        print(11, jedis.linsert(listName, BinaryClient.LIST_POSITION.AFTER, "4", "fd"));
        print(12, jedis.lrange(listName, 0, jedis.llen(listName) - 1));

        //hash
        String userKey = "userxx";
        jedis.hset(userKey, "name", "jim");
        jedis.hset(userKey, "age", "12");
        jedis.hset(userKey, "phone", "6651543468");
        print(12, jedis.hget(userKey, "name"));
        print(13, jedis.hgetAll(userKey));//嵌套key-value
        jedis.hdel(userKey, "phone");
        print(14, jedis.hgetAll(userKey));
        print(15, jedis.hexists(userKey, "name"));
        print(16, jedis.hexists(userKey, "phone"));
        print(17, jedis.hkeys(userKey));
        print(18, jedis.hvals(userKey));
        print(19, jedis.hsetnx(userKey, "school", "xidian"));
        print(20, jedis.hsetnx(userKey, "name", "mayuwan"));
        print(21, jedis.hgetAll(userKey));

        //set
        String like1 = "commentLike1";
        String like2 = "commentLike12";
        for (int i = 0; i < 10; i++) {
            jedis.sadd(like1, String.valueOf(i));
            jedis.sadd(like2, String.valueOf(i * 2));
        }
        print(22, jedis.smembers(like1));
        print(23, jedis.smembers(like2));
        print(24, jedis.sunion(like1, like2));
        print(25, jedis.sdiff(like1, like2));//第一个集合有，第二个集合没有
        print(26, jedis.sinter(like1, like2));
        print(26, jedis.sismember(like1, "245"));
        print(27, jedis.sismember(like2, "16"));
        jedis.srem(like1, "3");
        print(28, jedis.smembers(like1));
        jedis.smove(like2, like1, "18");
        print(29, jedis.smembers(like1));
        print(30, jedis.smembers(like2));
        print(31, jedis.scard(like1));

        //sorted set
        String rankKey = "rankKey";
        jedis.zadd(rankKey, 15, "jim");
        jedis.zadd(rankKey, 47, "Ben");
        jedis.zadd(rankKey, 65, "Doc");
        jedis.zadd(rankKey, 45, "Mei");
        jedis.zadd(rankKey, 95, "Kai");
        print(32, jedis.zcard(rankKey));
        print(33, jedis.zcount(rankKey, 0, 50));
        print(34, jedis.zscore(rankKey, "Kai"));
        jedis.zincrby(rankKey, 2, "Kai");
        print(35, jedis.zscore(rankKey, "Kai"));
        jedis.zincrby(rankKey, 2, "Ka");
        print(36, jedis.zscore(rankKey, "Ka"));
        print(37, jedis.zrange(rankKey, 0, 100));//默认从小到大排序
        print(38, jedis.zrevrange(rankKey, 0, 3));
        for (Tuple tuple : jedis.zrangeByScoreWithScores(rankKey, 60, 100)) {
            print(39, tuple.getElement() + ":" + String.valueOf(tuple.getScore()));
        }
        print(40, jedis.zrank(rankKey, "Kai"));
        print(41, jedis.zrevrank(rankKey, "Kai"));

        String setKey = "zset";
        jedis.zadd(setKey, 3, "a");
        jedis.zadd(setKey, 3, "b");
        jedis.zadd(setKey, 3, "c");
        jedis.zadd(setKey, 3, "d");
        jedis.zadd(setKey, 3, "e");
        jedis.zadd(setKey, 3, "f");
        print(42, jedis.zlexcount(setKey, "-", "+"));
        print(43, jedis.zlexcount(setKey, "[d", "[e"));
        jedis.zrem(setKey, "b");
        print(44, jedis.zrange(setKey, 0, 10));
        jedis.zremrangeByLex(setKey, "[d", "[f");
        print(45, jedis.zrange(setKey, 0, 10));

        //连接池
        JedisPool pool = new JedisPool();
        for (int i = 0; i < 100; i++) {
            Jedis j = pool.getResource();
            print(46, jedis.get("pv"));
            j.close();//要关
        }

        User user = new User();
        user.setName("xx");
        user.setSalt("salt");
        user.setPassword("gfdsfd");
        user.setHeadUrl("a.png");
        //对象序列化
        print(47,JSONObject.toJSONString(user));
        jedis.set("user1",JSONObject.toJSONString(user));
        String value = jedis.get("user1");
        User user2 =  JSON.parseObject(value,User.class);


    }
}

