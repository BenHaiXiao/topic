package com.github.benhaixiao.topic.event.folder;

import com.github.benhaixiao.topic.event.Event;
import com.github.benhaixiao.topic.follow.Follow;

/**
 * @author xiaobenhai
 *
 */
public class FolderFollowEvent extends Event {

    private String appid;
    private Follow follow;

    public FolderFollowEvent(){
        this.type = Type.FOLDER_FOLLOW;
    }

    public FolderFollowEvent(String appid,Follow follow){
        this.type = Type.FOLDER_FOLLOW;
        this.appid = appid;
        this.follow = follow;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public Follow getFollow() {
        return follow;
    }

    public void setFollow(Follow follow) {
        this.follow = follow;
    }
}
