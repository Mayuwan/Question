package com.newcoder.question.DAO;
import com.newcoder.question.Model.User;
//import org.apache.ibatis.annotations.*;

//@Mapper
public interface UserDAO {
    //@Insert
    int addUser(User user);
}
