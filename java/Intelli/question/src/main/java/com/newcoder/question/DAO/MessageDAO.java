package com.newcoder.question.DAO;

import com.newcoder.question.Model.Comment;
import com.newcoder.question.Model.Message;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MessageDAO {
    String TABLE_NAME = "message";
    String INSERT_FILEDS="from_id, to_id,content,conversation_id, created_date";
    String SELECT_FILEDS="id,"+INSERT_FILEDS;

    @Insert({"insert into ",TABLE_NAME, " (",INSERT_FILEDS,") ",
            "values(#{fromId},#{toId},#{content},#{conversationId},#{createdDate})"})
    void addMessage(Message comment);
}


