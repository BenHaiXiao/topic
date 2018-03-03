package com.github.benhaixiao.topic.domain;
import org.springframework.data.mongodb.core.mapping.Field;
/**
 * @author xiaobenhai
 *
 */
public class ImageSection extends Section{

    public ImageSection() {
        super();
        setType(Type.IMAGE);
    }
    @Field("attr")
    private Attr attr;
    @Field("image")
    private Image image;

    public Attr getAttr() {
        return attr;
    }

    public void setAttr(Attr attr) {
        this.attr = attr;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public static class Attr{
        @Field("width")
        private int width;
        @Field("height")
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