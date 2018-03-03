package com.github.benhaixiao.topic.domain;

import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author xiaobenhai
 *
 */
public class Tag {
    @Field("id")
    private int id;
    @Field("name")
    private String name;

    public Tag() {
    }

    public Tag(String name){
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
