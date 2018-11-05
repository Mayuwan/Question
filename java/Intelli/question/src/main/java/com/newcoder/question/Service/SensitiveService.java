package com.newcoder.question.Service;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.newcoder.question.Controller.LoginController;
import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.io.*;

@Service
public class SensitiveService implements InitializingBean{//初始化时读取文件
    private static final Logger logger = LoggerFactory.getLogger(SensitiveService.class);
    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("运行读取文件");
        try{
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("SensitiveWords.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while((line = reader.readLine())!=null){
                addNode(line);
            }
            reader.close();
        }catch (Exception e){
            logger.info("读取文件失败"+e.getMessage());
        }
    }
    public void addNode(String word){
        TrieNode tempNode  = root;
        for(int i=0;i<word.length();i++){
            Character key = word.charAt(i);
            if(isSymbol(key)) {
                continue;
            }
            if(tempNode.getSubNode(key)==null){
                TrieNode subNode = new TrieNode();
                tempNode.addSubNode(key,subNode);
            }

            tempNode = tempNode.getSubNode(key);
            if(i==word.length()-1){
                tempNode.setKeyEnd(true);
            }
        }
    }
    class TrieNode{
        //是不是关键字的结尾
        private boolean end = false;
        //当前节点下所有的子节点
        private Map<Character,TrieNode> subNodes = new HashMap<>();
        public TrieNode(){}
        public void addSubNode(Character key, TrieNode node){
            subNodes.put(key,node);
        }
        public TrieNode getSubNode(Character key){
            return subNodes.get(key);
        }
        public boolean isKeyEnd(){return end;}
        public void setKeyEnd(boolean end){this.end = end;}
    }
    private TrieNode root = new TrieNode();
    public SensitiveService(){}
    public String filter(String text){
        if(StringUtils.isBlank(text)) return text;

        String replacement = "***";
        TrieNode tempNode = root;
        int i=0,j=0;
        StringBuilder sb = new StringBuilder();
        while(j < text.length()){

            Character key = text.charAt(j);
            if(isSymbol(key)){//判断是否为非法文字
                if (tempNode == root){
                    sb.append(key);
                    i++;
                }
                j++;
                continue;
            }
            tempNode = tempNode.getSubNode(key);
            if(tempNode == null){
                sb.append(text.charAt(i));
                j=i+1;
                i=j;
                tempNode = root;//没有要回到根节点
            }
            else if(tempNode.isKeyEnd()){
                sb.append(replacement);
                j++;
                i=j;
                tempNode = root;
            }
            else {
                //tempNode = node.getSubNode(key);
                j++;
            }
        }
        sb.append(text.substring(i));
       return sb.toString();
    }
    /**
     * 过滤其他非法文字
     * True 非法文字*/
    private boolean isSymbol(char c){
        int intc = (int)c;
        ///东亚文字 0x2E80--0x9FFF
        return !CharUtils.isAsciiAlphanumeric(c) && (intc < 0x2E80 || intc > 0x9FFF);
    }

    public static void main(String[] args){
        SensitiveService service = new SensitiveService();
        service.addNode("色情");
        service.addNode("赌博");
        System.out.println(service.filter("你好  色 情 的故事 赌博 但是公司大股东"));
    }
}
