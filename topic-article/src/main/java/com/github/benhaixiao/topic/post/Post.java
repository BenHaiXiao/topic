package com.github.benhaixiao.topic.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.benhaixiao.topic.domain.Content;
import com.github.benhaixiao.topic.user.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

/**
 * @author xiaobenhai
 *
 */
@Document(collection = "post")
public class Post {

    @Id
    private String id;
    @JsonIgnore
    @Field("tid")
    private String targetId;
    @Field("ct")
    private Content content;
    @Field("user")
    private User creator;
    @Field("rid")
    private String replyId;
    @Field("acmCnt")
    private long acclaimCount;
    @Field("ctime")
    private Date createTime;
    @Field("utime")
    private Date updateTime;
    @JsonIgnore
    @Field("valid")
    private int valid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
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

    public String getReplyId() {
        return replyId;
    }

    public void setReplyId(String replyId) {
        this.replyId = replyId;
    }

    public long getAcclaimCount() {
        return acclaimCount;
    }

    public void setAcclaimCount(long acclaimCount) {
        this.acclaimCount = acclaimCount;
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
