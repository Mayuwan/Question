package com.newcoder.question.DAO;

import com.newcoder.question.Model.Comment;
import com.newcoder.question.Model.Message;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.jmx.export.assembler.MethodExclusionMBeanInfoAssembler;

import java.util.List;

@Mapper
public interface MessageDAO {
    String TABLE_NAME = "message";
    String INSERT_FILEDS=" from_id, to_id,content,conversation_id, created_date, has_read";
    String SELECT_FILEDS="id,"+INSERT_FILEDS;

    @Insert({"insert into ",TABLE_NAME, " (",INSERT_FILEDS,") ",
            "values(#{fromId},#{toId},#{content},#{conversationId},#{createdDate},#{hasRead})"})
    int addMessage(Message message);

    @Select({"select ",SELECT_FILEDS," from ",TABLE_NAME,
            " where conversation_id=#{conversationId} order by created_date desc limit #{offset}, #{limit}"})
    List<Message> getConversationDetail(@Param("conversationId") String conversationId,
                                   @Param("offset") int offset, @Param("limit") int limit);

    @Select({"select ", INSERT_FILEDS, " ,count(id) as id from ( select * from ", TABLE_NAME,
            " where from_id=#{userId} or to_id=#{userId} order by id desc) tt group by conversation_id  order by created_date desc limit #{offset}, #{limit}"})
    List<Message> getConversationList(@Param("userId") int userId,
                                      @Param("offset") int offset, @Param("limit") int limit);

    @Select({"select count(id) from ",TABLE_NAME,"" +
            " where conversation_id=#{conversationId} and to_id=#{userId} and has_read=#{hasRead}"})
    int getConversationUnreadCount(@Param("userId") int userId,
                                   @Param("conversationId") String conversationId);



}


