package com.newcoder.question.Service;

import com.newcoder.question.DAO.QuestionDAO;
import com.newcoder.question.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.newcoder.question.Model.Question;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@Service
public class QuestionService {
    @Autowired
    QuestionDAO questionDAO;
    @Autowired
    SensitiveService sensitiveService;
    public List<Question> selectLatestQuestions(int userId, int offset, int limit){
       return questionDAO.selectLatestQuestions(userId,offset,limit);
    }

    public int addQuestion(Question question){
        //敏感词过滤，过滤html标签
        question.setContent(HtmlUtils.htmlEscape(question.getContent()));
        question.setTitle(HtmlUtils.htmlEscape(question.getTitle()));
        //敏感词过滤 色情，低俗等内容
        //前缀树算法：将敏感词创建为一个树，根据树来查找
        sensitiveService.filter(question.getContent());
        sensitiveService.filter(question.getTitle());
        return questionDAO.addQuestion(question);
    }

    public Question getQuestionById(int id){
        return questionDAO.selectQuestionById(id);
    }
}
