package com.newcoder.question.Controller;

import com.newcoder.question.Model.*;
import com.newcoder.question.Service.CommentService;
import com.newcoder.question.Service.FollowService;
import com.newcoder.question.Service.QuestionService;
import com.newcoder.question.Service.UserService;
import com.newcoder.question.async.EventModel;
import com.newcoder.question.async.EventProducer;
import com.newcoder.question.async.EventType;
import com.newcoder.question.util.WendaUtil;
import javafx.beans.binding.ObjectExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.test.ImportAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.jws.WebParam;
import javax.swing.text.View;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class FollowController {
    public static final Logger logger = LoggerFactory.getLogger(FollowController.class);

    @Autowired
    FollowService followService;
    @Autowired
    HostHolder hostHolder;
    @Autowired
    EventProducer eventProducer;
    @Autowired
    QuestionService questionService;
    @Autowired
    UserService userService;
    @Autowired
    CommentService commentService;

    @RequestMapping(path = {"followUser"}, method = RequestMethod.POST)
    @ResponseBody
    public String followUser(@RequestParam("userId") int userId){
        if(hostHolder.getUser() == null){
            return WendaUtil.getJsonString(999);
        }
        User localUser = hostHolder.getUser();
        boolean ret = followService.follow(localUser.getId(),EntityType.ENTITY_USER,userId);
        //包装成一个事件
        eventProducer.fireEvent(new EventModel(EventType.FOLLOW)
                .setActionId(localUser.getId())
                .setEntityType(EntityType.ENTITY_USER).setEntityId(userId)
                .setEntityOwnerId(userId));

        //页面展示关注人数
        return WendaUtil.getJsonString( ret ? 0 : 1 ,
                String.valueOf(followService.getFolloweeCount(localUser.getId(),EntityType.ENTITY_USER)));
    }

    @RequestMapping(path = {"unfollowUser"}, method = RequestMethod.POST)
    @ResponseBody
    public String unfollowUser(@RequestParam("userId") int userId){
        if(hostHolder.getUser() == null){
            return WendaUtil.getJsonString(999);
        }
        User localUser = hostHolder.getUser();
        boolean ret = followService.unfollow(localUser.getId(),EntityType.ENTITY_USER,userId);
        //包装成一个事件
        eventProducer.fireEvent(new EventModel(EventType.FOLLOW)
                .setActionId(localUser.getId())
                .setEntityType(EntityType.ENTITY_USER).setEntityId(userId)
                .setEntityOwnerId(userId));
        //页面展示关注人数
        return WendaUtil.getJsonString( ret ? 0 : 1 ,
                String.valueOf(followService.getFolloweeCount(localUser.getId(),EntityType.ENTITY_USER)));
    }

    @RequestMapping(path = {"followQuestion"}, method = RequestMethod.POST)
    @ResponseBody
    public String followQuestion(@RequestParam("questionId") int questionId){
        if(hostHolder.getUser() == null){
            return WendaUtil.getJsonString(999);
        }
        if(questionService.getQuestionById(questionId) == null){
            return WendaUtil.getJsonString(0,"问题不存在");
        }
        Question question = questionService.getQuestionById(questionId);
        boolean ret = followService.follow(hostHolder.getUser().getId(),EntityType.ENTITY_QUESTION,questionId);

        eventProducer.fireEvent(new EventModel(EventType.FOLLOW)
                .setActionId(hostHolder.getUser().getId())
                .setEntityType(EntityType.ENTITY_QUESTION).setEntityId(questionId)
                .setEntityOwnerId(question.getUserId()));

        //页面返回关注者的信息以及关注人数
        Map<String,Object> map = new HashMap<>();
        map.put("headUrl",hostHolder.getUser().getHeadUrl());
        map.put("name",hostHolder.getUser().getName());
        map.put("id",hostHolder.getUser().getId());
        map.put("count",followService.getFolloweeCount(hostHolder.getUser().getId(),EntityType.ENTITY_QUESTION));
        return WendaUtil.getJsonString( ret ? 0 : 1 , map);
    }

    @RequestMapping(path = {"unfollowQuestion"}, method = RequestMethod.POST)
    @ResponseBody
    public String unfollowQuestion(@RequestParam("questionId") int questionId){
        if(hostHolder.getUser() == null){
            return WendaUtil.getJsonString(999);
        }
        if(questionService.getQuestionById(questionId) == null){
            return WendaUtil.getJsonString(0,"问题不存在");
        }
        Question question = questionService.getQuestionById(questionId);
        boolean ret = followService.unfollow(hostHolder.getUser().getId(),EntityType.ENTITY_QUESTION,questionId);

        //包装成一个事件
        eventProducer.fireEvent(new EventModel(EventType.FOLLOW)
                .setActionId(hostHolder.getUser().getId())
                .setEntityType(EntityType.ENTITY_QUESTION).setEntityId(questionId)
                .setEntityOwnerId(question.getUserId()));
        //页面返回关注者的信息以及关注人数
        Map<String,Object> map = new HashMap<>();
        //map.put("id",hostHolder.getUser().getId());
        map.put("count",followService.getFolloweeCount(hostHolder.getUser().getId(),EntityType.ENTITY_QUESTION));
        return WendaUtil.getJsonString( ret ? 0 : 1 , map);
    }

    @RequestMapping(path = {"/user/{userId}/followers"}, method = RequestMethod.GET)
    public String followers(@PathVariable("userId") int userId, Model model){
        List<Integer> followers = followService.getFollowers(EntityType.ENTITY_USER,userId,100);
        //logger.info("粉丝数:"+String.valueOf(followers.size()));
        int localUser =0;
        if(hostHolder.getUser() != null){
            localUser = hostHolder.getUser().getId();
        }
        //页面返回用户以及粉丝数，关注数，是否关注

        model.addAttribute("curUser",userService.selectById(userId));
        model.addAttribute("followerCount",followService.getFollowersCount(EntityType.ENTITY_USER,userId));
        model.addAttribute("followers",getuserInfo(localUser,followers));
        return "followers";
    }

    @RequestMapping(path = {"/user/{userId}/followees"}, method = RequestMethod.GET)
    public String followees(@PathVariable("userId") int userId, Model model){
        List<Integer> followees = followService.getFollowees(userId,EntityType.ENTITY_USER,100);
        int localUser =0;
        if(hostHolder.getUser() != null){
            localUser = hostHolder.getUser().getId();
        }
        //页面返回用户以及粉丝数，关注数，是否关注

        model.addAttribute("curUser",userService.selectById(userId));
        model.addAttribute("followeeCount",followService.getFolloweeCount(EntityType.ENTITY_USER,userId));
        model.addAttribute("followees",getuserInfo(localUser,followees));
        return "followees";
    }

    private List<ViewObject> getuserInfo(int localUserId,List<Integer> userIds){
        List<ViewObject> view = new ArrayList<>();
        for(Integer id:userIds){
            User user = userService.selectById(id);
            if(user == null){
                continue;
            }
            ViewObject vos  = new ViewObject();
            vos.set("user",user);
            vos.set("followerCount",followService.getFollowersCount(EntityType.ENTITY_USER,id));
            vos.set("followeeCount",followService.getFolloweeCount(id,EntityType.ENTITY_USER));
            vos.set("commentCount",commentService.getUserCommentCount(id));
            if(localUserId != 0){
                vos.set("followed",followService.isFollower(localUserId,EntityType.ENTITY_USER,id));
            }
            else{
                vos.set("followed",false);
            }
            view.add(vos);
        }
        return view;
    }
}
