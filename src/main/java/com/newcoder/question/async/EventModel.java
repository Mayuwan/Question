package com.newcoder.question.async;

import java.util.HashMap;
import java.util.Map;
/**
 * 事件的抽象**/
public class EventModel {
    private EventType eventType;//事件类型
    private int entityId;//对谁发生了事件
    private int entityType;
    private int actionId;//事件触发者 谁触发的事件
    private int entityOwnerId;//对谁发生了事件的拥有者

    private Map<String,String> exts = new HashMap<>();//拓展字段

    public EventModel(){}
    public EventModel(EventType type){
        this.eventType = type;
    }


    public EventType getEventType() {
        return eventType;
    }

    public EventModel setEventType(EventType eventType) {
        this.eventType = eventType;
        return this;
    }

    public int getEntityId() {
        return entityId;
    }

    public EventModel setEntityId(int entityId) {
        this.entityId = entityId;
        return this;
    }

    public int getEntityType() {
        return entityType;
    }

    public EventModel setEntityType(int entityType) {
        this.entityType = entityType;
        return this;
    }

    public int getActionId() {
        return actionId;
    }

    public EventModel setActionId(int actionId) {
        this.actionId = actionId;
        return this;
    }

    public int getEntityOwnerId() {
        return entityOwnerId;
    }

    public EventModel setEntityOwnerId(int entityOwnerId) {
        this.entityOwnerId = entityOwnerId;
        return this;
    }

    public Map<String, String> getExts() {
        return exts;
    }
    public String getExts(String key){
        return exts.get(key);
        //return this;//eventModel.set(xxx).set().set
    }

    public EventModel setExts(Map<String, String> exts) {
        this.exts = exts;
        return this;
    }
    public EventModel setExts(String key, String value){
        exts.put(key,value);
        return this;//eventModel.set(xxx).set().set
    }
}

