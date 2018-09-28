package com.newcoder.question.Service;

import com.newcoder.question.DAO.UserDAO;
import com.newcoder.question.util.WendaUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.newcoder.question.Model.User;
import java.util.*;
@Service
public class UserService {
    @Autowired
    UserDAO userDAO;
    public User selectById(int id){
        return userDAO.selectById(id);
    }

    public Map<String,String> register(String name,String password){
        Map<String,String> map = new HashMap<>();
        if(StringUtils.isBlank(name)){
            map.put("msg","名字不能为空");
            return map;
        }
        if(StringUtils.isBlank(password)){
            map.put("msg","密码不能为空");
            return map;
        }
        if(userDAO.selectByName(name)!=null){//检查用户名是否重复
            map.put("msg","用户名已经被注册");
            return map;
        }
        User user = new User(name);
        user.setSalt(UUID.randomUUID().toString().substring(0,8));//加盐，密码不易被攻破
        user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png",new Random().nextInt(100)));
        user.setPassword(WendaUtil.MD5(password+user.getSalt()));
        userDAO.addUser(user);
        return map;
    }

}
