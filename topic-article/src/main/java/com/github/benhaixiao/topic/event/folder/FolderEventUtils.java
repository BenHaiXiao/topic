package com.github.benhaixiao.topic.event.folder;

import com.github.benhaixiao.topic.folder.Folder;
import com.github.benhaixiao.topic.follow.Follow;

/**
 * @author xiaobenhai
 *
 */
public class FolderEventUtils {

    public static FolderCreateEvent createFolderCreateEvent(String appid,Folder folder){
        return new FolderCreateEvent(appid,folder);
    }

    public static FolderDeleteEvent createFolderDeleteEvent(String appid,Folder folder){
        return new FolderDeleteEvent(appid,folder);
    }

    public static FolderModifyEvent createFolderModifyEvent(String appid,Folder oldFolder,Folder newFolder){
        return new FolderModifyEvent(appid,oldFolder,newFolder);
    }

    public static FolderFollowEvent createFolderFollowEvent(String appid,Follow follow){
        return new FolderFollowEvent(appid,follow);
    }

    public static FolderUnFollowEvent createFolderUnFollowEvent(String appid,Follow follow){
        return new FolderUnFollowEvent(appid,follow);
    }
}
