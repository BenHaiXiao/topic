package com.github.benhaixiao.topic.admin.util;

import com.github.benhaixiao.topic.shared.Status;

import java.util.HashMap;

/**
 * @author xiaobenhai
 *
 */
public class SlothResponse extends HashMap<String,Object> {

    public SlothResponse(){
        put("code", Status.SUCCESS.getCode());
    }

    public SlothResponse code(int code){
        put("code",code);
        return this;
    }

    public SlothResponse code(Status status){
        return code(status.getCode());
    }

    public SlothResponse add(String key,Object data){
        put(key,data);
        return this;
    }
}
