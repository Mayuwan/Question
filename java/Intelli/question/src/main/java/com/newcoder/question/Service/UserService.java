package com.newcoder.question.Service;

import com.newcoder.question.DAO.LoginTicketDAO;
import com.newcoder.question.DAO.UserDAO;
import com.newcoder.question.Model.LoginTicket;
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

    @Autowired
    LoginTicketDAO loginTicketDAO;

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
        //注册添加新用户
        User user = new User(name);
        user.setSalt(UUID.randomUUID().toString().substring(0,8));//加盐，密码不易被攻破
        user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png",new Random().nextInt(100)));
        user.setPassword(WendaUtil.MD5(password+user.getSalt()));
        userDAO.addUser(user);
        //添加ticket
        String ticket = addLoginTicket(user.getId());
        map.put("ticket",ticket);
        return map;
    }

    public Map<String,String> login(String username, String password){
        Map<String,String> map = new HashMap<>();
        if(StringUtils.isBlank(username)){
            map.put("msg","用户名不能为空");
            return map;
        }
        if(StringUtils.isBlank(password)){
            map.put("msg","密码不能为空");
            return map;
        }
        User user = userDAO.selectByName(username);
        if(user == null){
            map.put("msg","不存在该用户");
            return map;
        }
        //验证密码是否相同
        if(!WendaUtil.MD5(password+user.getSalt()).equals(user.getPassword())){
            map.put("msg","密码错误");
            return map;
        }
        String ticket = addLoginTicket(user.getId());
        map.put("ticket",ticket);
        return map;
    }
    public String addLoginTicket(int userId){
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setStatus(0);
        loginTicket.setUserId(userId);
        loginTicket.setTicket(UUID.randomUUID().toString().replace("-",""));
        Date date = new Date();
        date.setTime(3600*24*100+date.getTime());
        loginTicket.setExpired(date);
        loginTicketDAO.addTicket(loginTicket);
        return loginTicket.getTicket();
    }

    public void logout(String ticket){
        loginTicketDAO.updateStatus(1,ticket);
    }
}
