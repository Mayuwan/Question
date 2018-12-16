package com.newcoder.question.async.handler;

import com.alibaba.fastjson.JSONObject;
import com.newcoder.question.Model.*;
import com.newcoder.question.Service.*;
import com.newcoder.question.async.EventHandler;
import com.newcoder.question.async.EventModel;
import com.newcoder.question.async.EventType;
import com.newcoder.question.util.JedisAdapter;
import com.newcoder.question.util.RedisKeyUtil;
import com.newcoder.question.util.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class FeedHandler implements EventHandler {
    @Autowired
    UserService userService;
    @Autowired
    QuestionService questionService;
    @Autowired
    FeedService feedService;

    @Autowired
    FollowService followService;

    @Autowired
    JedisAdapter jedisAdapter;

    /**构建feed模板
     * 如果是评论问题；谁评论了什么问题
     * 如果是关注问题；谁关注了什么问题
     * 如果是评论
     * */
    private String BuildFeedData(EventModel eventModel){
        Map<String,String> map = new HashMap<>();
        User user = userService.selectById(eventModel.getActionId());
        if(user == null){
            return null;
        }
        map.put("userId",String.valueOf(user.getId()));
        map.put("userHead",user.getHeadUrl());
        map.put("userName",user.getName());

        if(eventModel.getEventType() == EventType.COMMENT ||
                ( eventModel.getEventType() == EventType.FOLLOW && eventModel.getEntityType() == EntityType.ENTITY_QUESTION)){
            Question question = questionService.getQuestionById(eventModel.getEntityId());
            if(question == null){
                return null;
            }
            map.put("questionId",String.valueOf(question.getId()));
            map.put("questionTitle",question.getTitle());
            map.put("type",String.valueOf(eventModel.getEventType().getValue()));
            return JSONObject.toJSONString(map);
        }
        return null;
    }
    @Override
    public void doHandle(EventModel eventModel) {
        Feed feed = new Feed();
        feed.setUserId(eventModel.getActionId());
        feed.setCreatedDate(new Date());
        feed.setType(eventModel.getEventType().getValue());
        feed.setData( BuildFeedData(eventModel) );
        if(feed.getData() == null){
            return;
        }
        feedService.addFeed(feed);//拉模式 存在数据库
        /*
        //给事件的粉丝推 推模式
        List<Integer> followers =  followService.getFollowers(EntityType.ENTITY_USER,eventModel.getActionId(),Integer.MAX_VALUE);
        followers.add(0);//系统id
        // 给所有粉丝推事件
        for(Integer f:followers){
            String redisTimelineKey = RedisKeyUtil.getTimelineKey(f);
            jedisAdapter.lPush(redisTimelineKey,String.valueOf(feed.getId()));
            // 限制最长长度，如果timelineKey的长度过大，就删除后面的新鲜事
        }
        */
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(new EventType[]{EventType.COMMENT,EventType.FOLLOW});
    }
}
