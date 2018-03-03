package com.github.benhaixiao.topic.domain;
import org.springframework.data.mongodb.core.mapping.Field;
/**
 * @author xiaobenhai
 *
 */
public class TextSection extends Section{

    public TextSection() {
        super();
        setType(Type.TEXT);
    }
    @Field("attr")
    private Attr attr;
    @Field("text")
    private String text;

    public Attr getAttr() {
        return attr;
    }

    public void setAttr(Attr attr) {
        this.attr = attr;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public static class Attr{
        @Field("length")
        private int length;

        public int getLength() {
            return length;
        }

        public void setLength(int length) {
            this.length = length;
        }
    }
}
