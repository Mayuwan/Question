package com.newcoder.question.async;

import java.util.List;
import java.util.ListResourceBundle;

public interface EventHandler {
    void doHandle(EventModel EventModel);

    List<EventType> getSupportEventTypes();
}
