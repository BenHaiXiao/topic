package com.github.benhaixiao.topic.event.folder;

import com.github.benhaixiao.topic.folder.Folder;
import com.github.benhaixiao.topic.event.Event;

/**
 * @author xiaobenhai
 *
 */
public class FolderDeleteEvent extends Event {
    private String appid;
    private Folder folder;

    public FolderDeleteEvent(){
        this.type = Type.FOLDER_DELETE;
    }

    public FolderDeleteEvent(String appid,Folder folder) {
        this.type = Type.FOLDER_DELETE;
        this.appid = appid;
        this.folder = folder;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public Folder getFolder() {
        return folder;
    }

    public void setFolder(Folder folder) {
        this.folder = folder;
    }
}
