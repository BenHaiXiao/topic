package com.github.benhaixiao.topic.event.folder;

import com.github.benhaixiao.topic.event.Event;
import com.github.benhaixiao.topic.folder.Folder;

/**
 * @author xiaobenhai
 *
 */
public class FolderCreateEvent extends Event {
    private String appid;
    private Folder folder;

    public FolderCreateEvent(){
        this.type = Type.FOLDER_CREATE;
    }

    public FolderCreateEvent(String appid,Folder folder) {
        this.type = Type.FOLDER_CREATE;
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
