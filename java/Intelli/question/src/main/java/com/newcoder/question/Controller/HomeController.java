package com.newcoder.question.Controller;

import com.newcoder.question.Model.Question;
import com.newcoder.question.Model.ViewObject;
import com.newcoder.question.Service.QuestionService;
import com.newcoder.question.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    QuestionService questionService;
    @Autowired
    UserService userService;

    public List<ViewObject> getQuestions(int userId, int offset, int limit){
        List<Question> questionsList  =questionService.selectLatestQuestions(userId,offset,limit);
        List<ViewObject> vos = new ArrayList<>();
        for(Question question: questionsList){
            ViewObject vo = new ViewObject();
            vo.set("question",question);
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

    @RequestMapping(path = "/user/{useId}",method = RequestMethod.GET)
    public String userIndex(Model model,
                            @PathVariable("useId") int useId){
        model.addAttribute("vos",getQuestions(useId,0,10));
        return "index";
    }

}
