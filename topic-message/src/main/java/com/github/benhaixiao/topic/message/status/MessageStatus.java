package com.github.benhaixiao.topic.message.status;

import com.google.common.collect.Maps;

import java.util.EnumSet;
import java.util.Map;

/**
 * @author xiaobenhai
 *
 */
public enum MessageStatus {

    NEW(1),READED(2),DELETE(3);

    private int code;
    private static Map<Integer,MessageStatus> lookup = Maps.newHashMap();
    private MessageStatus(int code){
        this.code = code;
    }
    static {
        for(MessageStatus status : EnumSet.allOf(MessageStatus.class)){
            lookup.put(status.code,status);
        }
    }

    public MessageStatus valueOf(int code){
        return lookup.get(code);
    }

    public int getCode(){
        return code;
    }

}
