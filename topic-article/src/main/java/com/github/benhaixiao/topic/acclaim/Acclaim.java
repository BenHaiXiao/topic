package com.github.benhaixiao.topic.acclaim;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

/**
 * @author xiaobenhai
 *
 */
@Document(collection = "acclaim")
public class Acclaim {
    @Field("uid")
    private String uid;
    @Field("tid")
    private String targetId;
    @Field("ctime")
    private Date createTime;

    public Acclaim() {
    }

    public Acclaim(String uid, String targetId) {
        this.uid = uid;
        this.targetId = targetId;
        this.createTime = new Date();
    }
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
