package com.newcoder.question.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
@Controller
public class IndexController {
    @RequestMapping(path = {"/","/index"})
    @ResponseBody
    public String index(){
        return "hello spring";
    }
    @RequestMapping(path={"/profile/{groupId}/{userId}"})
    @ResponseBody
    public String profile(@PathVariable("userId") int userId,
                          @PathVariable("groupId") String groupId,
                          @RequestParam(value = "key") String key,
                          @RequestParam(value = "type",defaultValue = "1") int type){
        return String.format("Profile Page of %s / %d, t:%d k: %s ", groupId, userId, type, key );
    }


    @RequestMapping(path = {"vm"})
    @ResponseBody
    public String template(Model model){
        return "home";
    }
}
