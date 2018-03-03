package com.github.benhaixiao.topic.event.folder;

import com.github.benhaixiao.topic.event.Event;
import com.github.benhaixiao.topic.folder.Folder;

/**
 * @author xiaobenhai
 *
 */
public class FolderModifyEvent extends Event {
    private String appid;
    private Folder oldFolder;
    private Folder newFolder;

    public FolderModifyEvent(){
        this.type = Type.FOLDER_MODIFY;
    }

    public FolderModifyEvent(String appid,Folder oldFolder,Folder newFolder) {
        this.type = Type.FOLDER_MODIFY;
        this.appid = appid;
        this.oldFolder = oldFolder;
        this.newFolder = newFolder;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public Folder getOldFolder() {
        return oldFolder;
    }

    public void setOldFolder(Folder oldFolder) {
        this.oldFolder = oldFolder;
    }
}
