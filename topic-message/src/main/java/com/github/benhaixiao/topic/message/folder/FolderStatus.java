package com.github.benhaixiao.topic.message.folder;


/**
 * @author xiaobenhai
 *
 */

public class FolderStatus {

    private String version;
    private long totalMsgNum;
    private long newMsgNum;

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

    public long getNewMsgNum() {
        return newMsgNum;
    }

    public void setNewMsgNum(long newMsgNum) {
        this.newMsgNum = newMsgNum;
    }
}
