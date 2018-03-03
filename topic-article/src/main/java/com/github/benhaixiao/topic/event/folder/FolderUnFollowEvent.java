package com.github.benhaixiao.topic.event.folder;

import com.github.benhaixiao.topic.event.Event;
import com.github.benhaixiao.topic.follow.Follow;

/**
 * @author xiaobenhai
 *
 */
public class FolderUnFollowEvent extends Event {

    private String appid;
    private Follow follow;

    public FolderUnFollowEvent(){
        this.type = Type.FOLDER_UNFOLLOW;
    }

    public FolderUnFollowEvent(String appid,Follow follow){
        this.type = Type.FOLDER_UNFOLLOW;
        this.appid = appid;
        this.follow = follow;
    }

    public Follow getFollow() {
        return follow;
    }

    public void setFollow(Follow follow) {
        this.follow = follow;
    }
}
