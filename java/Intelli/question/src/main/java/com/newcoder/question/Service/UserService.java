package com.newcoder.question.Service;

import com.newcoder.question.DAO.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.newcoder.question.Model.User;
@Service
public class UserService {
    @Autowired
    UserDAO userDAO;
    public User selectById(int id){
        return userDAO.selectById(id);
    }
}
