package com.newcoder.question.async.handler;

import com.newcoder.question.Service.SearchSerivce;
import com.newcoder.question.async.EventHandler;
import com.newcoder.question.async.EventModel;
import com.newcoder.question.async.EventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class AddQuestionHandler implements EventHandler {
    private static final Logger logger = LoggerFactory.getLogger(AddQuestionHandler.class);
    @Autowired
    SearchSerivce searchSerivce;

    @Override
    public void doHandle(EventModel EventModel) {
        try{
            searchSerivce.indexQuestion(EventModel.getEntityId(),
                    EventModel.getExts("question_title"),EventModel.getExts("question_content"));
        }
        catch (Exception e){
            logger.error("添加搜索题目索引失败");
        }

    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.ADD_QUESTION);
    }
}
