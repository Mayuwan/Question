package com.newcoder.question.async;

import com.alibaba.fastjson.JSON;
import com.newcoder.question.util.JedisAdapter;
import com.newcoder.question.util.RedisKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EventConsumer implements InitializingBean,ApplicationContextAware {
    public static final Logger logger = LoggerFactory.getLogger(EventConsumer.class);

    private Map<EventType,List<EventHandler>> config = new HashMap<>();//初始化映射关系
    private ApplicationContext applicationContext;

    @Autowired
    JedisAdapter jedisAdapter;
    @Override
    public void afterPropertiesSet() throws Exception {
        //从上下文中找到所有实现EventHandler的类,初始化映射关系config
        Map<String,EventHandler> beans = applicationContext.getBeansOfType(EventHandler.class);
        if(beans!=null){
            for(Map.Entry<String,EventHandler> bean : beans.entrySet()){
                List<EventType> eventTypes = bean.getValue().getSupportEventTypes();

               for(EventType type: eventTypes){
                   if(!config.containsKey(type)){
                       config.put(type,new ArrayList<EventHandler>());
                   }
                   config.get(type).add(bean.getValue());
               }
            }
        }

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String eventKey = RedisKeyUtil.getEventKey();
                List<String> events = jedisAdapter.brpop(0,eventKey);
                if(events!=null){
                    for(String event:events){
                        if(event.equals(eventKey)){
                            continue;
                        }
                        EventModel eventModel = JSON.parseObject(event,EventModel.class);
                        if(!config.containsKey(eventModel.getEventType())){
                            logger.error("不能识别的事件");
                        }
                        //找到感兴趣的handler
                        for(EventHandler eventHandler: config.get(eventModel.getEventType())){
                            eventHandler.doHandle(eventModel);
                        }
                    }
                }
            }
        });
        thread.start();

    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
