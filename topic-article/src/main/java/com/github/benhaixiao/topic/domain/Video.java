package com.github.benhaixiao.topic.domain;

/**
 * @author xiaobenhai
 *
 */
public class Video {
    private String url;
    private Media media;
    private long duration;
    private String imageUrl;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public static enum Media{
        MP4,FLV,WMV,RMVB
    }
}
