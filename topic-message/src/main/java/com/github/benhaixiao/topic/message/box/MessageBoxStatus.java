package com.github.benhaixiao.topic.message.box;

import com.github.benhaixiao.topic.message.folder.FolderStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author xiaobenhai
 *
 */
@Document(collection = "message_box_stats")
public class MessageBoxStatus {

    @Id
    private String uid;
    @Field("version")
    private String version;
    @Field("totalMsg")
    private long totalMsgNum;
    @Field("newMsg")
    private int newMsgNum;
    @Field("notice")
    private FolderStatus notice;
    @Field("reply")
    private FolderStatus reply;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public long getTotalMsgNum() {
        return totalMsgNum;
    }

    public void setTotalMsgNum(long totalMsgNum) {
        this.totalMsgNum = totalMsgNum;
    }

    public int getNewMsgNum() {
        return newMsgNum;
    }

    public void setNewMsgNum(int newMsgNum) {
        this.newMsgNum = newMsgNum;
    }

    public FolderStatus getNotice() {
        return notice;
    }

    public void setNotice(FolderStatus notice) {
        this.notice = notice;
    }

    public FolderStatus getReply() {
        return reply;
    }

    public void setReply(FolderStatus reply) {
        this.reply = reply;
    }
}
