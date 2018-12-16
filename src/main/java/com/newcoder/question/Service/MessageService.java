package com.newcoder.question.Service;

import com.newcoder.question.DAO.CommentDAO;
import com.newcoder.question.DAO.MessageDAO;
import com.newcoder.question.Model.Comment;
import com.newcoder.question.Model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@Service
public class MessageService {
    @Autowired
    MessageDAO messageDAO;
    @Autowired
    SensitiveService sensitiveService;

    public List<Message> getConversationDetail(String conversationId, int offset,int limit){
        return messageDAO.getConversationDetail(conversationId,offset,limit);
    }
    public List<Message> getConversationList(int userID, int offset,int limit){
        return messageDAO.getConversationList(userID,offset,limit);
    }

    public int addMessage(Message message){
        //对comment内容进行敏感词过滤
        message.setContent(HtmlUtils.htmlEscape(message.getContent()));
        message.setContent(sensitiveService.filter(message.getContent()));
        return messageDAO.addMessage(message) > 0 ? message.getId() : 0;
    }
     public int getConversationUnreadCount(int userId, String conversationId){
        return messageDAO.getConversationUnreadCount(userId, conversationId);
     }

}
