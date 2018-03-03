package com.github.benhaixiao.topic.domain;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author xiaobenhai
 *
 */
public class Marks {
    private int size;
    private List<Tag> tags;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public Marks add(Tag tag){
        if(tags == null){
            tags = Lists.newArrayList();
        }
        tags.add(tag);
        size = tags.size();
        return this;
    }
}
