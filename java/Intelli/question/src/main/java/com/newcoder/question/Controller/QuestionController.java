package com.newcoder.question.Controller;

import com.newcoder.question.DAO.QuestionDAO;
import com.newcoder.question.Model.HostHolder;
import com.newcoder.question.Model.Question;
import com.newcoder.question.Service.QuestionService;
import com.newcoder.question.Service.UserService;
import com.newcoder.question.util.WendaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
public class QuestionController {
    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    QuestionService questionService;

    @Autowired
    UserService userService;
    @Autowired
    HostHolder hostHolder;

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
        model.addAttribute("user",userService.selectById(question.getUserId()));
        return "detail";
    }
}
