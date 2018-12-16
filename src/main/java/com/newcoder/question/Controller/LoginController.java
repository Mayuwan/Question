package com.newcoder.question.Controller;

import com.newcoder.question.Aspect.LogAspect;
import com.newcoder.question.DAO.LoginTicketDAO;
import com.newcoder.question.Model.HostHolder;
import com.newcoder.question.Model.User;
import com.newcoder.question.Service.UserService;
import com.newcoder.question.async.EventModel;
import com.newcoder.question.async.EventProducer;
import com.newcoder.question.async.EventType;
import com.newcoder.question.util.JedisAdapter;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sun.swing.StringUIClientPropertyKey;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    UserService userService;

    @Autowired
    EventProducer eventProducer;


    @RequestMapping(path = {"/reg/"},method = {RequestMethod.POST})
    public String register(Model model,
                           @RequestParam("username") String username,
                           @RequestParam("password") String password,
                           @RequestParam(value = "next", required = false) String next,
                           @RequestParam(value = "rememberme",defaultValue = "false") boolean rememberme,
                           HttpServletResponse response){
        try{
            Map<String,String> map = userService.register(username,password);
            if(map.containsKey("ticket")){
                Cookie cookie = new Cookie("ticket",map.get("ticket"));
                cookie.setPath("/");
                response.addCookie(cookie);
                if(StringUtils.isNotBlank(next)){
                    return "redirect:"+next;
                }
                return "redirect:/";
            }
            else{
                model.addAttribute("msg",map.get("msg"));
                return "login";
            }

        }catch (Exception e){
            logger.info("注册异常"+e.getMessage());
            return "login";
        }
    }


    @RequestMapping
            (path = {"/reglogin"},method = {RequestMethod.GET})
    public String relogin(Model model,
                          @RequestParam(value = "next", required = false) String next){
        if(StringUtils.isNotBlank(next)){
            model.addAttribute("next",next);
        }
        return "login";
    }

    @RequestMapping(path = {"/login/"},method = {RequestMethod.POST})
    public String login(Model model,
                        @RequestParam("username") String username,
                        @RequestParam("password") String password,
                        @RequestParam(value = "next", required = false) String next,
                        @RequestParam(value = "rememberme",defaultValue = "false") boolean rememberme,
                        HttpServletResponse response){
        try{
            Map<String,String> map = userService.login(username,password);

            if(map.containsKey("ticket")){
                Cookie cookie = new Cookie("ticket",map.get("ticket"));
                cookie.setPath("/");//啥意思？
                response.addCookie(cookie);

                eventProducer.fireEvent(new EventModel(EventType.LOGIN).setActionId(Integer.valueOf(map.get("userId")))
                        .setExts("username",username)
                        .setExts("email", "zjuyxy@qq.com"));

                if(StringUtils.isNotBlank(next)){//判断是否为未登录跳转
                    return "redirect:"+next;
                }
                return "redirect:/";
            }else{
                model.addAttribute("msg",map.get("msg"));
                return "login";
            }
        }catch (Exception e){
            logger.info("注册异常"+e.getMessage());
            return "login";
        }
    }

    @RequestMapping(path = {"/logout"},method = {RequestMethod.GET})
    public String login(@CookieValue("ticket") String ticket){
        userService.logout(ticket);
        return "redirect:/";
    }
}
