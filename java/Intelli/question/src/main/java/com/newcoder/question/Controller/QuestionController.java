package com.newcoder.question.Controller;

import com.newcoder.question.DAO.QuestionDAO;
import com.newcoder.question.Model.*;
import com.newcoder.question.Service.*;
import com.newcoder.question.util.WendaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class QuestionController {
    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    QuestionService questionService;

    @Autowired
    UserService userService;
    @Autowired
    HostHolder hostHolder;
    @Autowired
    CommentService commentService;
    @Autowired
    LikeService likeService;
    @Autowired
    FollowService followService;

    @RequestMapping(path = "/question/add", method = {RequestMethod.POST})
    @ResponseBody
    //返回json格式
    public String addQuestion(@RequestParam("title") String title,
                              @RequestParam("content") String content){
        try{
            Question question = new Question();
            question.setTitle(title);
            question.setContent(content);
            question.setCreatedDate(new Date());
            question.setCommentCount(0);
            if(hostHolder.getUser() == null){//匿名用户
                //question.setUserId(WendaUtil.ANONYMOUS_USERID);
                return WendaUtil.getJsonString(999);
            }else{
                question.setUserId(hostHolder.getUser().getId());
            }
            if(questionService.addQuestion(question) >0){
                return WendaUtil.getJsonString(0);
            }
        }catch (Exception e){
            logger.info("增加问题失败"+e.getMessage());
        }
        return WendaUtil.getJsonString(1,"发布问题失败");
    }

    @RequestMapping(path = "/question/{questionId}",method = RequestMethod.GET)
    public String questionIndex(Model model,
                                @PathVariable("questionId") int questionId){
        Question question = questionService.getQuestionById(questionId);
        model.addAttribute("question",question);

        List<Comment>  commentList = commentService.getCommentsByEntity(questionId,EntityType.ENTITY_QUESTION);
        List<ViewObject> comments = new ArrayList<>();
        for(Comment comment : commentList){
            ViewObject vos = new ViewObject();
            vos.set("comment",comment);

            if(hostHolder.getUser() == null){
                vos.set("liked",0);
            }else{
                vos.set("liked",likeService.getLikeStatus(hostHolder.getUser().getId(),EntityType.ENTITY_COMMENT,comment.getId()));
            }
            vos.set("likeCount",likeService.getLikeCount(comment.getId(),EntityType.ENTITY_COMMENT));
            vos.set("user",userService.selectById(comment.getUserId()));
            comments.add(vos);
        }
        model.addAttribute("comments",comments);

        List<Integer> followersId = followService.getFollowers(EntityType.ENTITY_QUESTION,questionId,20);
        List<ViewObject> followUsers = new ArrayList<>();
        for(Integer userId : followersId){
            ViewObject vo = new ViewObject();
            User u = userService.selectById(userId);
            if (u == null) {
                continue;
            }
            vo.set("name", u.getName());
            vo.set("headUrl", u.getHeadUrl());
            vo.set("id", u.getId());
            followUsers.add(vo);
        }
        model.addAttribute("followUsers",followUsers);

        if (hostHolder.getUser() != null) {
            model.addAttribute("followed", followService.isFollower(hostHolder.getUser().getId(), EntityType.ENTITY_QUESTION, questionId));
        } else {
            model.addAttribute("followed", false);
        }



        //model.addAttribute("user",userService.selectById(question.getUserId()));
        return "detail";
    }
}
