package com.github.benhaixiao.topic.message.event;

import com.github.benhaixiao.topic.event.Event;

/**
 * @author xiaobenhai
 *
 */
public interface EventHandler {
    void process(Event event);
}
