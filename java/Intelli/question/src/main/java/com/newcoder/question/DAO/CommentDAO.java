package com.newcoder.question.DAO;

import com.newcoder.question.Model.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper
public interface CommentDAO {
    String TABLE_NAME = "comment";
    String INSERT_FILEDS=" content, user_id,created_date,entity_id,entity_type";
    String SELECT_FILEDS="id,"+INSERT_FILEDS;

    @Insert({"insert into ",TABLE_NAME, " (",INSERT_FILEDS,") ",
            "values(#{comment},#{userId},#{createdDate},#{entityId},#{entityType})"})
    void addComment(Comment comment);
}


