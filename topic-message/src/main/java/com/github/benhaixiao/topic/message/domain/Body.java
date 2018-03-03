package com.github.benhaixiao.topic.message.domain;

import com.github.benhaixiao.topic.domain.Content;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author xiaobenhai
 *
 */
public class Body {
    @Field("content")
    private Content content;
    @Field("action")
    private Action action;

    @Override
    public String toString() {
        return "Body{" +
                "content=" + content +
                ", action=" + action +
                '}';
    }
}
