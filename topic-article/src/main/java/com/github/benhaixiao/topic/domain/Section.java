package com.github.benhaixiao.topic.domain;

import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author xiaobenhai
 *
 */
public abstract class Section{
    @Field("type")
    protected Type type;
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public static enum Type{
        TEXT,IMAGE,AUDIO,VIDEO
    }
}

