package com.newcoder.question.Controller;

import com.newcoder.question.Model.HostHolder;
import com.newcoder.question.Model.Message;
import com.newcoder.question.Model.User;
import com.newcoder.question.Model.ViewObject;
import com.newcoder.question.Service.MessageService;
import com.newcoder.question.Service.UserService;
import com.newcoder.question.util.WendaUtil;
import org.apache.struts.chain.commands.ExceptionCatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.swing.text.View;
import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class MessageController {
    public static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    MessageService messageService;
    @Autowired
    HostHolder hostHolder;
    @Autowired
    UserService userService;


    @RequestMapping(path = "/msg/addMessage", method = RequestMethod.POST)
    public String getMessage(@RequestParam("toName") String toName,
                             @RequestParam("content") String content){
        try{
            if(hostHolder.getUser() == null){
                return WendaUtil.getJsonString(999,"未登录");
            }
            User to = userService.selectByName(toName);
            if(to == null){
                return WendaUtil.getJsonString(999,"用户不存在");
            }
            Message message = new Message();
            message.setContent(content);
            message.setCreatedDate(new Date());
            message.setToId(to.getId());
            int fromId = hostHolder.getUser().getId();
            message.setFromId(fromId);
            message.setHasRead(0);
            message.setConversationId(String.valueOf(fromId)+String.valueOf(to.getId()));
            messageService.addMessage(message);

            return WendaUtil.getJsonString(0);
        }catch (Exception e){
            logger.info("发送消息失败"+e.getMessage());
            return WendaUtil.getJsonString(999,"发送消息失败");
        }
    }
    @RequestMapping(path = "/msg/list", method = RequestMethod.GET)
    public String getConversationList(Model model){
        if(hostHolder.getUser()==null){
            return "redirect:/relogin";
        }
        int localId = hostHolder.getUser().getId();
        List<Message> messageList = messageService.getConversationList(localId,0,10);
        List<ViewObject> messages = new ArrayList<>();
        for(Message message : messageList){
            ViewObject vo = new ViewObject();
            vo.set("message",message);
            int targetId = (localId == message.getFromId()) ? message.getToId():message.getFromId();
            vo.set("user",userService.selectById(targetId));//看其他人
            vo.set("unread",messageService.getConversationUnreadCount(localId,message.getConversationId()));
            messages.add(vo);
        }
        model.addAttribute("conversations",messages);
        return "letter";
    }
    @RequestMapping(path = "/msg/detail", method = RequestMethod.GET)
    public String getConversationDetail(Model model,
                                        @PathVariable("conversationId") String conversationId){
        try{
            List<Message> messageList =  messageService.getConversationDetail(conversationId,0,10);
            List<ViewObject> messages= new ArrayList<>();
            for(Message msg:messageList){
                ViewObject v = new ViewObject();
                v.set("message",msg);
                v.set("user",userService.selectById(msg.getFromId()));
                messages.add(v);
            }
            model.addAttribute("messages",messages);
        }catch (Exception e){
            logger.error("获取详情失败"+e.getMessage());
        }
        return "letterDetail";
    }
}
