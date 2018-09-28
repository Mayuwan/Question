package com.newcoder.question.Controller;

import com.newcoder.question.Aspect.LogAspect;
import com.newcoder.question.Service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.*;

@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    UserService userService;

    @RequestMapping(path = {"/reg/"},method = {RequestMethod.POST})
    public String register(Model model,
                           @PathVariable("username") String username,
                           @PathVariable("password") String password){
        System.out.println("sgabghmsregujfdngpagjd");
        try{
            Map<String,String> map = userService.register(username,password);
            if(map.containsKey("msg")){
                model.addAttribute("msg",map.get("msg"));
                return "login";
            }
            return "redirect:/";
        }catch (Exception e){
            logger.info("注册异常"+e.getMessage());
            return "login";
        }
    }


    @RequestMapping(path = {"rereg"},method = {RequestMethod.GET})
    public String rereg(){
        return "login";
    }

}
