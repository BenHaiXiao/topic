package com.github.benhaixiao.topic.message.domain;

import com.google.common.collect.Maps;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Map;


/**
 * @author xiaobenhai
 *
 */
public class Action {
    @Field("type")
    private int type;
    @Field("params")
    private Map<String,String> params;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Action addParams(String key,String value){
        if(params == null){
            params = Maps.newHashMap();
        }
        params.put(key,value);
        return this;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Action{type=");
        sb.append(type);
        sb.append(", params=");
        if(params != null){
            for(Map.Entry<String,String> param : params.entrySet()){
                sb.append(param.getKey());
                sb.append(":");
                sb.append(param.getValue());
                sb.append("-");
            }
        }
        sb.append('}');
        return sb.toString();
    }
}
