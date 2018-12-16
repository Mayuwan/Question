package com.newcoder.question.async.handler;

import com.newcoder.question.Model.EntityType;
import com.newcoder.question.Model.Message;
import com.newcoder.question.Model.User;
import com.newcoder.question.Service.MessageService;
import com.newcoder.question.Service.QuestionService;
import com.newcoder.question.Service.UserService;
import com.newcoder.question.async.EventHandler;
import com.newcoder.question.async.EventModel;
import com.newcoder.question.async.EventType;
import com.newcoder.question.util.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class FollowHandler implements EventHandler {
    @Autowired
    MessageService messageService;
    @Autowired
    UserService userService;
    @Autowired
    QuestionService questionService;
    @Override
    public void doHandle(EventModel eventModel) {
        Message message  = new Message();
        message.setFromId(WendaUtil.SYSTEM_USERID);
        message.setToId(eventModel.getEntityOwnerId());
        message.setCreatedDate(new Date());
        message.setHasRead(0);
        User user = userService.selectById(eventModel.getActionId());
        if(user == null){
            return;
        }
        if(eventModel.getEventType().getValue()==EntityType.ENTITY_USER){
            message.setContent("用户:"+user.getName()+
                    "关注了你, http://127.0.0.1:8080/user/"+eventModel.getEntityId());
        }
        else if(eventModel.getEventType().getValue()==EntityType.ENTITY_QUESTION){
            message.setContent("用户:"+user.getName()+
                    "关注了你的问题, http://127.0.0.1:8080/question/"+eventModel.getActionId());
        }
        messageService.addMessage(message);

    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.FOLLOW);
    }
}
