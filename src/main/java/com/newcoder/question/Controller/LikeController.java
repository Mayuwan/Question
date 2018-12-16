package com.newcoder.question.Controller;

import com.newcoder.question.Model.Comment;
import com.newcoder.question.Model.EntityType;
import com.newcoder.question.Model.HostHolder;
import com.newcoder.question.Service.CommentService;
import com.newcoder.question.Service.LikeService;
import com.newcoder.question.async.EventModel;
import com.newcoder.question.async.EventProducer;
import com.newcoder.question.async.EventType;
import com.newcoder.question.util.WendaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LikeController {
    public static final Logger logger = LoggerFactory.getLogger(LikeController.class);
    @Autowired
    LikeService likeService;

    @Autowired
    CommentService commentService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    EventProducer eventProducer;

    @RequestMapping(path = "/like",method = RequestMethod.POST)
    @ResponseBody
    public String like(@RequestParam("commentId") int commentId){
        if(hostHolder.getUser() == null){
            return WendaUtil.getJsonString(999);
        }
                Comment comment = commentService.selectCommentById(commentId);
        eventProducer.fireEvent(new EventModel(EventType.LIKE)
                .setActionId(hostHolder.getUser().getId())
                .setEntityId(commentId).setEntityType(EntityType.ENTITY_COMMENT)
                .setEntityOwnerId(comment.getUserId())
                .setExts("questionId",String.valueOf(comment.getEntityId())));

        likeService.like(hostHolder.getUser().getId(),EntityType.ENTITY_COMMENT,commentId);
        long likeCount = likeService.getLikeCount(EntityType.ENTITY_COMMENT,commentId);
        return WendaUtil.getJsonString(0,String.valueOf(likeCount));
    }

    @RequestMapping(path = "/dislike",method = RequestMethod.POST)
    @ResponseBody
    public String disLike(@RequestParam("commentId") int commentId){
        if(hostHolder.getUser() == null){
            return WendaUtil.getJsonString(999);
        }
        likeService.disLike(hostHolder.getUser().getId(),EntityType.ENTITY_COMMENT,commentId);
        long likeCount = likeService.getLikeCount(EntityType.ENTITY_COMMENT,commentId);

        return WendaUtil.getJsonString(0,String.valueOf(likeCount));
    }

}
