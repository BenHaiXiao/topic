package com.github.benhaixiao.topic.node;

import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Map;

/**
 * @author xiaobenhai
 *
 */
public class NodeData {
    @Field("name")
    private String name;
    @Field("desc")
    private String description;
    @Field("exts")
    private Map<String,String> exts;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, String> getExts() {
        return exts;
    }

    public void setExts(Map<String, String> exts) {
        this.exts = exts;
    }
}
