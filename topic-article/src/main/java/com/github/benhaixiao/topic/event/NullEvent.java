package com.github.benhaixiao.topic.event;

/**
 * @author xiaobenhai
 *
 */
public class NullEvent extends Event {

    private static NullEvent instance = new NullEvent();

    public static NullEvent getInstance(){
        return instance;
    }

    private NullEvent(){
        this.type = Type.NULL;
    }
}
