package com.github.benhaixiao.topic.shared;

import com.github.benhaixiao.topic.event.Event;

/**
 * @author xiaobenhai
 *
 */
public class EventJacksonSerializer {
    private static final JsonMapper jsonMapper = JsonMapper.nonNullMapper();

    public static String serialize(Event event){
        return jsonMapper.toJson(event);
    }
}
