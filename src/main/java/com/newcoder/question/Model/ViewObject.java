package com.newcoder.question.Model;

import java.util.*;

public class ViewObject {
    private Map<String,Object> objs = new HashMap();

    public void set(String key, Object object){
        objs.put(key,object);
    }
    public Object get(String key){
        return objs.get(key);
    }
}
