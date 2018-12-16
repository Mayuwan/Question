package com.newcoder.question.Controller;

import com.newcoder.question.Model.*;
import com.newcoder.question.Service.CommentService;
import com.newcoder.question.Service.FollowService;
import com.newcoder.question.Service.QuestionService;
import com.newcoder.question.Service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {
    public static final Logger logger = LoggerFactory.getLogger(HomeController.class);
    @Autowired
    QuestionService questionService;
    @Autowired
    UserService userService;
    @Autowired
    FollowService followService;
    @Autowired
    CommentService commentService;
    @Autowired
    HostHolder hostHolder;

    public List<ViewObject> getQuestions(int userId, int offset, int limit){
        List<Question> questionsList  =questionService.selectLatestQuestions(userId,offset,limit);
        List<ViewObject> vos = new ArrayList<>();
        for(Question question: questionsList){
            ViewObject vo = new ViewObject();
            vo.set("question",question);
            vo.set("followCount",followService.getFollowersCount(EntityType.ENTITY_QUESTION,question.getId()));
            vo.set("user",userService.selectById(question.getUserId()));
            vos.add(vo);
        }
        return vos;
    }

    @RequestMapping(path = {"/","/index"},method = {RequestMethod.GET})
    public String index(Model model){
        model.addAttribute("vos",getQuestions(0,0,10));
        return "index";
    }

    @RequestMapping(path = "/user/{userId}",method = RequestMethod.GET)
    public String userIndex(Model model,
                            @PathVariable("userId") int userId){
        /*model.addAttribute("vos",getQuestions(userId,0,10));
        return "index";*/
        model.addAttribute("vos", getQuestions(userId, 0, 10));

        User user = userService.selectById(userId);
        ViewObject vo = new ViewObject();
        vo.set("user", user);
        vo.set("commentCount", commentService.getUserCommentCount(userId));
        vo.set("followerCount", followService.getFollowersCount(EntityType.ENTITY_USER, userId));
        vo.set("followeeCount", followService.getFolloweeCount(userId, EntityType.ENTITY_USER));
        if (hostHolder.getUser() != null) {
            vo.set("followed", followService.isFollower(hostHolder.getUser().getId(), EntityType.ENTITY_USER, userId));
        } else {
            vo.set("followed", false);
        }
        model.addAttribute("profileUser", vo);
        return "profile";

    }




}
