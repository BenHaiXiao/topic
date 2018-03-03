package com.github.benhaixiao.topic.event;

import com.github.benhaixiao.topic.kafka.KProducer;

/**
 * @author xiaobenhai
 *
 */
public class EventManager {

    private static KProducer kProducer = null;
    public static void raiseEvent(Event event){
        if(kProducer == null){
            kProducer = new KProducer();
            kProducer.init();
        }
        kProducer.send(event);
    }
}
