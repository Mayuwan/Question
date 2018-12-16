package com.newcoder.question.Service;

import com.newcoder.question.Model.Question;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SearchSerivce {
    private static final String urlString = "http://127.0.0.1:8983/solr/wenda";
    private static final String QUESTION_TITLE_FIELD = "question_title";
    private static final String QUESTION_CONTENT_FIELD = "question_content";

    HttpSolrClient solr = new HttpSolrClient.Builder(urlString).build();

    public List<Question> searchQuestion(String keyStr, int offset, int count, String hlPre, String hlPost)throws Exception{
        List<Question> questionList  = new ArrayList<>();

        SolrQuery query = new SolrQuery(keyStr);
        query.setRows(count);
        query.setStart(offset);
        query.setHighlight(true);
        query.setHighlightSimplePre(hlPre);
        query.setHighlightSimplePost(hlPost);
        query.set("hl.fl",QUESTION_TITLE_FIELD+","+QUESTION_CONTENT_FIELD);

        QueryResponse response = solr.query(query);

        for(Map.Entry<String,Map<String, List<String>>> entry : response.getHighlighting().entrySet()){
            Question question = new Question();
            question.setId( Integer.valueOf(entry.getKey()));
            if(entry.getValue().containsKey(QUESTION_TITLE_FIELD)){
                List<String> TitleList = entry.getValue().get(QUESTION_TITLE_FIELD);
                if(TitleList.size() > 0){
                    question.setTitle(TitleList.get(0));
                }

            }
            if(entry.getValue().containsKey(QUESTION_CONTENT_FIELD)){
                List<String> contentList = entry.getValue().get(QUESTION_CONTENT_FIELD);
                if(contentList.size() > 0){
                    question.setContent(contentList.get(0));
                }

            }
            questionList.add(question);
        }
        return questionList;
    }

    public boolean indexQuestion(int questionId, String title, String content)throws Exception{
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id", questionId);
        document.addField(QUESTION_TITLE_FIELD, title);
        document.addField(QUESTION_CONTENT_FIELD, content);
        UpdateResponse response = solr.add(document,1000);
        return response != null && response.getStatus()==0;
    }


}