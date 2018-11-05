package com.newcoder.question.Model;

import com.alibaba.fastjson.JSONObject;

import java.util.Date;

public class Feed {
    private int id;
    private int type;
    private int userId;
    private Date createdDate;
    private String data;//json格式

    private JSONObject datajson=null;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
        datajson  = JSONObject.parseObject(data);
    }
    public String get(String key){
        if(datajson != null){
            return datajson.getString(key);
        }
        return null;
    }
}
