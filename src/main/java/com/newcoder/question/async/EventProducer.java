package com.newcoder.question.async;

import com.alibaba.fastjson.JSONObject;
import com.newcoder.question.Controller.CommentController;
import com.newcoder.question.util.JedisAdapter;
import com.newcoder.question.util.RedisKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventProducer {
    public static final Logger logger = LoggerFactory.getLogger(EventProducer.class);
    @Autowired
    JedisAdapter jedisAdapter;
    public boolean fireEvent(EventModel eventModel){
        try{//将eventModel转化成序列化
            String objValue = JSONObject.toJSONString(eventModel);
            String eventKey = RedisKeyUtil.getEventKey();
            jedisAdapter.lPush(eventKey,objValue);
            return true;
        }catch (Exception e){
            logger.error("fire event失败"+e.getMessage());
            return false;
        }
    }
}
