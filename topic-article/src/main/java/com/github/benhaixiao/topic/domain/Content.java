package com.github.benhaixiao.topic.domain;

import com.google.common.collect.Lists;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * @author xiaobenhai
 *
 */
public class Content{
    @Field("size")
    private long size;
    @Field("sections")
    private List<Section> sections;

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public void addSection(Section section){
        if(sections == null){
            sections = Lists.newArrayList();
        }
        sections.add(section);
        setSize(sections.size());
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }
}
