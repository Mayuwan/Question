package com.newcoder.question.DAO;

import com.newcoder.question.Model.Comment;
import org.apache.ibatis.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

@Mapper
public interface CommentDAO {
    String TABLE_NAME = "comment";
    String INSERT_FILEDS=" content, user_id, created_date, entity_id, entity_type, status";
    String SELECT_FILEDS="id,"+INSERT_FILEDS;

    //添加评论
    @Insert({"insert into ",TABLE_NAME, " (",INSERT_FILEDS,") ",
            "values(#{content},#{userId},#{createdDate},#{entityId},#{entityType},#{status})"})
    int addComment(Comment comment);

    //查找某个实体下的所有评论
    @Select({"select ",SELECT_FILEDS," from ",TABLE_NAME,
            " where entity_id=#{entityId} and entity_type=#{entityType} order by created_date desc"})
    List<Comment> selectCommentsByEntity(@Param("entityId") int entityId,
                                           @Param("entityType") int entityType);

    @Select({"select ",SELECT_FILEDS , " from ", TABLE_NAME, " where id=#{id}"})
    Comment selectCommentById(int id);

    //得到评论数
    @Select({"select count(id) from " ,TABLE_NAME," where entity_id=#{entityId} and entity_type=#{entityType}"})
    int getCommentCount(@Param("entityId") int entityId,
                        @Param("entityType") int entityType);
    @Select({"select count(id) from " , TABLE_NAME," where user_id=#{userId}"})
    int getUserCommentCount(@Param("userId") int userId);
    //更新status
    @Update({"update ",TABLE_NAME," set status=#{status} where id=#{id}"})
    int updateStatus(@Param("status") int status,
                      @Param("id") int id );
}


