package com.github.benhaixiao.topic.article;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.benhaixiao.topic.domain.Thumbs;
import com.github.benhaixiao.topic.domain.Content;
import com.github.benhaixiao.topic.domain.Marks;
import com.github.benhaixiao.topic.user.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

/**
 * @author xiaobenhai
 *
 */
@Document(collection = "article")
public class Article {

    @Id
    private String nodeId;
    @Field("fpath")
    private String folderPath;
    @Field("title")
    private String title;
    @Field("brief")
    private String brief;
    @Field("marks")
    private Marks marks;
    @Field("thumbs")
    private Thumbs thumbs;
    @Field("content")
    private Content content;
    @Field("creator")
    private User creator;
    @Field("ctime")
    private Date createTime;
    @Field("utime")
    private Date updateTime;
    @JsonIgnore
    @Field("valid")
    private int valid;

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getFolderPath() {
        return folderPath;
    }

    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public Marks getMarks() {
        return marks;
    }

    public void setMarks(Marks marks) {
        this.marks = marks;
    }

    public Thumbs getThumbs() {
        return thumbs;
    }

    public void setThumbs(Thumbs thumbs) {
        this.thumbs = thumbs;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public int getValid() {
        return valid;
    }

    public void setValid(int valid) {
        this.valid = valid;
    }
}
