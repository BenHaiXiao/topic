package com.github.benhaixiao.topic.domain;

import com.google.common.collect.Lists;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * @author xiaobenhai
 *
 */
public class Thumbs {
    @Field("size")
    private int size;
    @Field("images")
    private List<Image> images;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void addImage(Image image){
        if(images == null){
            images = Lists.newArrayList();
        }
        images.add(image);
        size = images.size();
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }
}