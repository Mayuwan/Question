package com.newcoder.question.Controller;

import com.newcoder.question.Model.*;
import com.newcoder.question.Service.FeedService;
import com.newcoder.question.Service.FollowService;
import com.newcoder.question.util.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class FeedController {
    @Autowired
    FeedService feedService;
    @Autowired
    HostHolder hostHolder;
    @Autowired
    FollowService followService;


    /**拉模式：从关注的对象中将他们的新鲜事取出
     * */
    @RequestMapping(path={"pullfeeds"},method = RequestMethod.GET)
    public String pullfeeds(Model model){
        User user = hostHolder.getUser();
        List<Integer> userIds = new ArrayList<>();
        List<Feed> feedList = new ArrayList<>();
        if(user == null){
            feedList = feedService.selectUserFeeds(100,userIds,10);
        }
        //找关注对象的最新动态
        userIds =  followService.getFollowees(user.getId(),EntityType.ENTITY_USER,Integer.MAX_VALUE);
        feedList = feedService.selectUserFeeds(100,userIds,10);

        model.addAttribute("feeds",feedList);


        return "feeds";
    }

}
