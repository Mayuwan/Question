package com.newcoder.question.Service;

import com.newcoder.question.DAO.FeedDAO;
import com.newcoder.question.Model.Feed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedService {
    @Autowired
    FeedDAO feedDAO;

    public List<Feed> selectUserFeeds(int maxId, List<Integer> userIds, int count){
        return feedDAO.selectUserFeeds(maxId,userIds,count);
    }

    public boolean addFeed(Feed feed){
        return feedDAO.addFeed(feed) > 0;
    }

    public Feed getFeedById(int id){
        return feedDAO.selectFeedById(id);
    }
}
