package com.github.benhaixiao.topic.domain;

/**
 * @author xiaobenhai
 *
 */
public class AudioSection extends Section{

    private Attr attr;
    private Audio audio;

    public AudioSection(){
        super();
        setType(Type.AUDIO);
    }

    public Attr getAttr() {
        return attr;
    }

    public void setAttr(Attr attr) {
        this.attr = attr;
    }

    public Audio getAudio() {
        return audio;
    }

    public void setAudio(Audio audio) {
        this.audio = audio;
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
