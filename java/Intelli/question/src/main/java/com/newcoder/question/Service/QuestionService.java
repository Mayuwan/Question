package com.newcoder.question.Service;

import com.newcoder.question.DAO.QuestionDAO;
import com.newcoder.question.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.newcoder.question.Model.Question;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    QuestionDAO questionDAO;
    public List<Question> selectLatestQuestions(int userId, int offset, int limit){
       return questionDAO.selectLatestQuestions(userId,offset,limit);
    }
}
