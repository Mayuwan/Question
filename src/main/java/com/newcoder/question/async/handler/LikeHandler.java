package com.newcoder.question.async.handler;

import com.newcoder.question.Model.HostHolder;
import com.newcoder.question.Model.Message;
import com.newcoder.question.Model.User;
import com.newcoder.question.Service.MessageService;
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
public class LikeHandler implements EventHandler {
    @Autowired
    MessageService messageService;
    @Autowired
    UserService userService;
    @Override
    public void doHandle(EventModel eventModel) {
        Message message  = new Message();
        message.setFromId(WendaUtil.SYSTEM_USERID);
        message.setToId(eventModel.getEntityOwnerId());
        message.setCreatedDate(new Date());
        message.setHasRead(0);
        User user = userService.selectById(eventModel.getActionId());
        message.setContent("用户:"+user.getName()+
                "赞了你的评论, http://127.0.0.1:8080/question/"+eventModel.getExts("questionId"));
        messageService.addMessage(message);

    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LIKE);
    }
}
