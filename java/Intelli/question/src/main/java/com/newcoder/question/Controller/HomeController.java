package com.newcoder.question.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {
    @RequestMapping(path = {"/","/index"},method = {RequestMethod.GET})
    //@ResponseBody
    public String index(HttpSession session){
        return "index";
    }

}
