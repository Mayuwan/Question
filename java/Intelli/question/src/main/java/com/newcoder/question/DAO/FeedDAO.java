package com.newcoder.question.DAO;

import com.newcoder.question.Model.Feed;
import com.newcoder.question.Model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;

import java.util.List;

@Mapper
public interface FeedDAO {
    String TABLE_NAME = "feed";
    String INSERT_FIELDS = "user_id, type, data, created_date";
    String SLECT_FILEDS = "id, "+INSERT_FIELDS;

    @Insert({"insert into "+TABLE_NAME +" ("+ INSERT_FIELDS + ") " +
            "values (#{userId},#{type},#{data},#{createdDate})"})
    int addFeed(Feed feed);

    @Select({"select ",SLECT_FILEDS," from ", TABLE_NAME ," where id=#{id}"})
    Feed selectFeedById(int id);


    /**
     * 如果是未登录状态，就没有userIds，如果是登录状态，有uerIds
     * */
    List<Feed> selectUserFeeds(@Param("maxId") int maxId,
                               @Param("userIds") List<Integer> userIds,
                               @Param("count") int count);

}
