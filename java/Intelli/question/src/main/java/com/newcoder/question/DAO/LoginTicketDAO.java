package com.newcoder.question.DAO;

import com.newcoder.question.Model.LoginTicket;
import org.apache.ibatis.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper
public interface LoginTicketDAO {
    String TABLE_NAMR = "login_ticket";
    String INSERT_FILEDS = " user_id, ticket, expired, status";
    String SELECT_FIlEDS = "id,"+INSERT_FILEDS;

    @Insert({"insert into ",TABLE_NAMR, "( ",INSERT_FILEDS,
            ") values(#{userId},#{ticket},#{expired},#{status})" })
    int addTicket(LoginTicket ticket);

    //@Select({"select ",SELECT_FIlEDS," from ", TABLE_NAMR ," where name=#{name}"})
    @Select({"select ",SELECT_FIlEDS," from ",TABLE_NAMR ," where ticket=#{ticket}"})
    LoginTicket getTicketByTicket(String ticket);

    @Update({"update ",TABLE_NAMR,"set status=#{status} where ticket=#{ticket}"})
    void updateStatus(@Param("status") int status, @Param("ticket") String ticket);

    @Delete({"delete from ",TABLE_NAMR," where ticket=#{ticket}"})
    void deleteTicket(String ticket);
}