package com.newcoder.question.util;

public class RedisKeyUtil {
    private static String LIKE ="LIKE";
    private static String DIS_LIKE ="DIS_LIKE";
    private static String SPLIT = ":";
    private static String EVENT = "EVENT_QUEUE";
    private static String FOLLOWER = "FOLLOWER";//粉丝
    private static String FOLLOWEE = "FOLLOWEE";//关注对象

    private static String TIMELINE = "TIMELINE";

    public static String getFollowerKey(int entityType, int entityId){
        return  FOLLOWER+SPLIT+String.valueOf(entityType)+SPLIT+String.valueOf(entityId);
    }
    public static String getFolloweeKey(int userId, int entityType){
        return  FOLLOWEE+SPLIT+String.valueOf(userId)+SPLIT+String.valueOf(entityType);
    }
    public static String getEventKey(){
        return  EVENT;
    }
    public static String getLikeKey(int entityType, int entityId){
        return  LIKE+SPLIT+String.valueOf(entityType)+SPLIT+String.valueOf(entityId);
    }
    public static String getDisLikeKey(int entityType, int entityId){
        return  DIS_LIKE+SPLIT+String.valueOf(entityType)+SPLIT+String.valueOf(entityId);
    }

    public static String getTimelineKey(int userId){
        return TIMELINE+SPLIT+String.valueOf(userId);
    }
}
