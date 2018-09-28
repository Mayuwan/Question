package com.newcoder.question.Model;

public class Message {
    private int id;
    private int fromId;
    private int toId;
    private String content;
    private int conversationId;
    private int creadedDate;

    public Message(){}

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

    public int getConversationId() {
        return conversationId;
    }

    public void setConversationId(int conversationId) {
        this.conversationId = conversationId;
    }

    public int getCreadedDate() {
        return creadedDate;
    }

    public void setCreadedDate(int creadedDate) {
        this.creadedDate = creadedDate;
    }
}
