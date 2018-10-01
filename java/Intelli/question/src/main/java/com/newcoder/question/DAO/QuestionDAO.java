package com.newcoder.question.DAO;
import com.newcoder.question.Model.Question;
import com.newcoder.question.Model.User;
import org.apache.ibatis.annotations.*;
import java.util.*;

@Mapper
public interface QuestionDAO {
    String TABLE_NAME = "question";
    String INSERT_FIELDS = "title,content,user_id, created_date, comment_count";
    String SLECT_FILEDS = "id, "+INSERT_FIELDS;
    @Insert({"insert into "+TABLE_NAME +" ("+ INSERT_FIELDS + ") " +
            "values (#{title},#{content},#{userId},#{createdDate},#{commentCount})"})
    int addQuestion(Question user);

    @Select({"select ",SLECT_FILEDS," from ",TABLE_NAME," where id=#{id}"})
    Question selectQuestionById(int id);

    //@Select({"select ",SLECT_FILEDS," from ", TABLE_NAME ," where id=#{id}"})
    List<Question> selectLatestQuestions(@Param("userId") int userId ,
                               @Param("offset")  int offset,
                               @Param("limit") int limit);

    /*@Update({"update ",TABLE_NAME," set password=#{password} where id=#{id}"})
    void updatePassword(Question user);

    @Delete({"delete from ",TABLE_NAME ," where id=#{id}"})
    void deleteById(int id);
    */
}
