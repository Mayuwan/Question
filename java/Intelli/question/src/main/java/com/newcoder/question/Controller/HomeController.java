package com.newcoder.question.Controller;

import com.newcoder.question.Model.Question;
import com.newcoder.question.Model.ViewObject;
import com.newcoder.question.Service.QuestionService;
import com.newcoder.question.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    QuestionService questionService;
    @Autowired
    UserService userService;
    @RequestMapping(path = {"/","/index"},method = {RequestMethod.GET})
    //@ResponseBody
    public String index(Model model){
        List<Question> questionsList  =questionService.selectLatestQuestions(0,0,10);
        List<ViewObject> vos = new ArrayList<>();
        for(Question question: questionsList){
            ViewObject vo = new ViewObject();
            vo.set("question",question);
            vo.set("user",userService.selectById(question.getUserId()));
            vos.add(vo);
        }
        model.addAttribute("vos",vos);
        return "index";
    }

}
