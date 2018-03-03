package com.github.benhaixiao.topic.node;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.benhaixiao.topic.domain.Thumbs;
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
@Document(collection = "node_meta")
public class NodeMeta {

   // @JsonIgnore
    @Id
    private String id;
    @JsonProperty("folderPath")
    @Field("pfpath")
    private String parentFolderPath;
    @Field("type")
    private NodeType type;
    @Field("data")
    private NodeData data;
    @Field("thumbs")
    private Thumbs thumbs;
    @Field("marks")
    private Marks marks;
    @Field("stats")
    private NodeStats stats;
    @Field("creator")
    private User creator;
    @Field("ctime")
    private Date createTime;
    @Field("utime")
    private Date updateTime;
    @JsonIgnore
    @Field("valid")
    private int valid = 1;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentFolderPath() {
        return parentFolderPath;
    }

    public void setParentFolderPath(String parentFolderPath) {
        this.parentFolderPath = parentFolderPath;
    }

    public NodeType getType() {
        return type;
    }

    public void setType(NodeType type) {
        this.type = type;
    }

    public NodeData getData() {
        return data;
    }

    public void setData(NodeData data) {
        this.data = data;
    }

    public Thumbs getThumbs() {
        return thumbs;
    }

    public void setThumbs(Thumbs thumbs) {
        this.thumbs = thumbs;
    }

    public Marks getMarks() {
        return marks;
    }

    public void setMarks(Marks marks) {
        this.marks = marks;
    }

    public NodeStats getStats() {
        if(stats == null){
            return NodeStats.EmptyStats();
        }
        return stats;
    }

    public void setStats(NodeStats stats) {
        this.stats = stats;
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
