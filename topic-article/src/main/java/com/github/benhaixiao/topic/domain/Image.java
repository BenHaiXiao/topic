package com.github.benhaixiao.topic.domain;

import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author xiaobenhai
 *
 */
public class Image {
    @Field("url")
    private String url;
    @Field("width")
    private int width;
    @Field("height")
    private int height;
    @Field("altText")
    private String altText;

    public Image() {
    }

    public Image(String url, int width, int height) {
        this.url = url;
        this.width = width;
        this.height = height;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getAltText() {
        return altText;
    }

    public void setAltText(String altText) {
        this.altText = altText;
    }
}
