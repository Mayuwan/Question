package com.newcoder.question.Controller;

import com.newcoder.question.Model.Comment;
import com.newcoder.question.Model.EntityType;
import com.newcoder.question.Model.HostHolder;
import com.newcoder.question.Service.CommentService;
import com.newcoder.question.Service.QuestionService;
import com.newcoder.question.async.EventModel;
import com.newcoder.question.async.EventProducer;
import com.newcoder.question.async.EventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Controller
public class CommentController {
    public static final Logger logger = LoggerFactory.getLogger(CommentController.class);
    @Autowired
    CommentService commentService;
    @Autowired
    HostHolder hostHolder;
    @Autowired
    QuestionService questionService;
    @Autowired
    EventProducer eventProducer;
    @RequestMapping(path = "/addComment", method = RequestMethod.POST)
    public String addComment(@RequestParam("questionId") int questionId,
                           @RequestParam("content") String content){
        try{
            Comment comment = new Comment();
            comment.setContent(content);
            comment.setCreatedDate(new Date());
            comment.setStatus(0);
            comment.setEntityId(questionId);
            comment.setEntityType(EntityType.ENTITY_QUESTION);
            if(hostHolder.getUser() != null){
                comment.setUserId(hostHolder.getUser().getId());
            }else{
                return "redirect:/relogin";
            }
            commentService.addComment(comment);

            int count = commentService.getCommentCount(comment.getEntityId(),comment.getEntityType());
            questionService.updateCommentCount(comment.getEntityId(),count);

            eventProducer.fireEvent(new EventModel(EventType.COMMENT).setEntityType(comment.getEntityType()).setEntityId(questionId)
                    .setActionId(comment.getUserId()));

        }catch (Exception e){
            logger.error("增加评论失败："+e.getMessage());
        }
        return "redirect:/question/"+questionId;
    }
}
