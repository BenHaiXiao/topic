package com.github.benhaixiao.topic.domain;

/**
 * @author xiaobenhai
 *
 */
public class VideoSection extends Section {

    private Attr attr;
    private Video video;

    public VideoSection() {
        super();
        setType(Type.VIDEO);
    }

    public Attr getAttr() {
        return attr;
    }

    public void setAttr(Attr attr) {
        this.attr = attr;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public static class Attr{
        private int width;
        private int height;

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
    }
}
