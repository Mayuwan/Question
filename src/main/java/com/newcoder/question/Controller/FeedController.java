package com.newcoder.question.Controller;

import com.newcoder.question.Model.*;
import com.newcoder.question.Service.FeedService;
import com.newcoder.question.Service.FollowService;
import com.newcoder.question.util.JedisAdapter;
import com.newcoder.question.util.RedisKeyUtil;
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
    @Autowired
    JedisAdapter jedisAdapter;


    /**拉模式：从关注的对象中将他们的新鲜事取出
     * */
    @RequestMapping(path={"pullfeeds"},method = RequestMethod.GET)
    public String pullfeeds(Model model){
        List<Integer> userIds = new ArrayList<>();
        List<Feed> feedList;

        //判断登陆状态
        int localUser = hostHolder.getUser() == null ? 0 : hostHolder.getUser().getId();///未登陆，系统id

        if(localUser != 0){
            userIds =  followService.getFollowees(localUser,EntityType.ENTITY_USER,Integer.MAX_VALUE);
        }
        //找关注对象的最新动态,，如果未登陆userIds为空
        feedList = feedService.selectUserFeeds(Integer.MAX_VALUE, userIds,10);

        model.addAttribute("feeds",feedList);
        return "feeds";
    }

    /**推模式: 从redis中读取用户的feed事件*/
    @RequestMapping(path={"pushfeeds"},method = RequestMethod.GET)
    public String pushfeeds(Model model){
        List<Feed> feeds = new ArrayList<>();
        //判断登陆状态
        int localUserId = hostHolder.getUser() == null ? 0 : hostHolder.getUser().getId();///未登陆，系统id
        //读取每个用户的timeline的新鲜事
        List<String> feedIds = jedisAdapter.lrange(RedisKeyUtil.getTimelineKey(localUserId),0,10);

        for(String f:feedIds){
            Feed feed =  feedService.getFeedById(Integer.valueOf(f));
            if(feed == null){continue;}
            feeds.add(feed);
        }
        model.addAttribute("feeds",feeds);
        return "feeds";
    }
}
