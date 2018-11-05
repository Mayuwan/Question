package com.newcoder.question.async.handler;

import com.alibaba.fastjson.JSONObject;
import com.newcoder.question.Model.*;
import com.newcoder.question.Service.FeedService;
import com.newcoder.question.Service.MessageService;
import com.newcoder.question.Service.QuestionService;
import com.newcoder.question.Service.UserService;
import com.newcoder.question.async.EventHandler;
import com.newcoder.question.async.EventModel;
import com.newcoder.question.async.EventType;
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
        feedService.addFeed(feed);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(new EventType[]{EventType.COMMENT,EventType.FOLLOW});
    }
}
