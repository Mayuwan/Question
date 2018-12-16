package com.newcoder.question.Model;

import java.util.Date;

public class Message {
    private int id;
    private int fromId;
    private int toId;
    private String content;
    private String conversationId;//fromId(小)_toId(大)
    private Date createdDate;
    private int hasRead;//0:未读

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getConversationId() {
        if(fromId<toId){
            return String.format("%d_%d",fromId,toId);
        }
        else{
            return String.format("%d_%d",toId,fromId);
        }

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFromId() {
        return fromId;
    }

    public void setFromId(int fromId) {
        this.fromId = fromId;
    }

    public int getToId() {
        return toId;
    }

    public void setToId(int toId) {
        this.toId = toId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public int getHasRead() {
        return hasRead;
    }

    public void setHasRead(int hasRead) {
        this.hasRead = hasRead;
    }
}
