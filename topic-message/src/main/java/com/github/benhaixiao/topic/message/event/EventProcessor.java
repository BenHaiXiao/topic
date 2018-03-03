package com.github.benhaixiao.topic.message.event;

import com.google.common.collect.Maps;
import com.github.benhaixiao.topic.event.Event;

import java.util.Map;

/**
 * @author xiaobenhai
 *
 */
public class EventProcessor {

    static {
        register(Event.Type.ARTICLE_ACCLAIM,new AcclaimEventHandler());
    }

    private static final Map<Event.Type,EventHandler> eventHandlerMap = Maps.newConcurrentMap();

    public static void register(Event.Type eventType,EventHandler eventHandler){
        eventHandlerMap.put(eventType,eventHandler);
    }

    public static void deRegister(Event.Type eventType){
        eventHandlerMap.remove(eventType);
    }
}
