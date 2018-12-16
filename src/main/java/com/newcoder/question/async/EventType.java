package com.newcoder.question.async;

public enum EventType {
    LIKE(0),
    EMAIL(1),
    LOGIN(2),
    FOLLOW(3),
    UNFOLLOW(5),
    COMMENT(4),
    ADD_QUESTION(6);


    private int value;
    EventType(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
