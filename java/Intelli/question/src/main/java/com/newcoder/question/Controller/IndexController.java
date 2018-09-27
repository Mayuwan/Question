package com.newcoder.question.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import com.newcoder.question.Model.User;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.io.*;

//@Controller
public class IndexController {
    @RequestMapping(path = {"/","/index"},method = {RequestMethod.GET})
    @ResponseBody
    public String index(HttpSession session){
        return "hello spring"+session.getAttribute("msg");
    }

    @RequestMapping(path={"/profile/{groupId}/{userId}"})
    @ResponseBody
    public String profile(@PathVariable("userId") int userId,
                          @PathVariable("groupId") String groupId,
                          @RequestParam(value = "key") String key,
                          @RequestParam(value = "type",defaultValue = "1") int type){
        return String.format("Profile Page of %s / %d, t:%d k: %s ", groupId, userId, type, key );
    }


    @RequestMapping(path = {"/vm"},method={RequestMethod.GET})
    //@ResponseBody
    public String template(Model model){
        model.addAttribute("key1","value1");
        List<String> colors = Arrays.asList(new String[] {"RED","GREEN","BULE","GRAY"});
        Map<String,String> map  = new HashMap<>();
        model.addAttribute("colors",colors);

        map.put("k1","sdgsf");
        map.put("k2","dg ar");
        map.put("k3","sWEA");
        model.addAttribute("map",map);

        model.addAttribute("user",new User("mayuwan"));
        return "home";
    }
    @RequestMapping(path = {"/request"},method={RequestMethod.GET})
    @ResponseBody//返回文本
    public String request(Model mode, HttpServletRequest request,
                          HttpServletResponse response){
        StringBuilder sb  = new StringBuilder();
        Enumeration<String>headers =  request.getHeaderNames();
        while(headers.hasMoreElements()){
            String name = headers.nextElement();
            sb.append("header: "+name+"--"+request.getHeader(name)+"<br>");
        }

        if (request.getCookies()!=null){
            for(Cookie cookie:request.getCookies()){
                sb.append("Cookie:"+cookie.getName()+"Value:"+cookie.getValue()+"<br>");
            }
        }

        sb.append(request.getMethod()+"<br>");
        sb.append(request.getRequestURI()+"<br>");
        sb.append(request.getPathInfo()+"<br>");
        sb.append(request.getServletPath()+"<br>");


        response.addHeader("newcodoer","hello");
        response.addCookie(new Cookie("newcoder","mayuwan"));
        return sb.toString();
    }
    @RequestMapping(path = {"/redirect/{code}"},method={RequestMethod.GET})
    //@ResponseBody//返回文本
    public RedirectView request(@PathVariable("code") int code, HttpSession session){
        session.setAttribute("msg","jump from redirect");
        RedirectView view = new RedirectView("/",true);
        if(code == 301){
            view.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        }
        return view;
    }
    @RequestMapping(path = {"/admin/{code}"},method={RequestMethod.GET})
    @ResponseBody//返回文本
    public String admin(@PathVariable("code") String code){
       if("admin".equals(code)) return "true";
       throw new IllegalArgumentException("参数错误");
    }
    @ExceptionHandler
    @ResponseBody
    public String error(Exception e){
        return "Error: " +e.getMessage();
    }


}
