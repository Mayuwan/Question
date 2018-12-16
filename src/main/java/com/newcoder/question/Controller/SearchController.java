package com.newcoder.question.Controller;

import com.newcoder.question.Model.*;
import com.newcoder.question.Service.*;
import com.newcoder.question.async.EventModel;
import com.newcoder.question.async.EventProducer;
import com.newcoder.question.async.EventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class SearchController {
    public static final Logger logger = LoggerFactory.getLogger(SearchController.class);
    @Autowired
    FollowService followService;
    @Autowired
    UserService userService;
    @Autowired
    QuestionService questionService;
    @Autowired
    SearchSerivce searchSerivce;

    @RequestMapping(path = "/search", method = RequestMethod.GET)
    public String search(@RequestParam("q") String keyStr,
                         @RequestParam( value = "offset",defaultValue = "0")int offset,
                         @RequestParam(value = "count", defaultValue = "10") int count , Model model){
        logger.info("搜索中");
        try{
            List<Question> questionList= searchSerivce.searchQuestion(keyStr,offset,count,"<em>","</em>");
            logger.info(String.valueOf(questionList.size()));
            List<ViewObject> vos = new ArrayList<>();

            for (Question q:questionList){
                ViewObject v = new ViewObject();
                Question dbQuestion = questionService.getQuestionById(q.getId());
                if(q.getContent()!=null){
                    dbQuestion.setContent(q.getContent());
                }
                if (q.getTitle() !=null) {
                    dbQuestion.setTitle(q.getTitle());
                }
                v.set("user",userService.selectById(dbQuestion.getUserId()));
                v.set("question",dbQuestion);
                v.set("followCount",followService.getFollowersCount(EntityType.ENTITY_QUESTION,dbQuestion.getId()));
                vos.add(v);
            }

            model.addAttribute("vos",vos);

        }catch (Exception e){
            logger.error("搜索失败："+e.getMessage());
        }
        return "result";
    }
}
