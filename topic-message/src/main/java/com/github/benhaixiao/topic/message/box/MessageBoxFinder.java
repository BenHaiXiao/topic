package com.github.benhaixiao.topic.message.box;

import com.github.benhaixiao.topic.message.domain.User;
import com.github.benhaixiao.topic.message.folder.FolderFinder;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Set;

/**
 * @author xiaobenhai
 *
 */
public class MessageBoxFinder {

    private FolderFinder folderFinder;
    private MessageBoxStatusStore messageBoxStatusStore;

    public MessageBoxFinder(FolderFinder folderFinder,MessageBoxStatusStore messageBoxStatusStore){
        this.folderFinder = folderFinder;
        this.messageBoxStatusStore = messageBoxStatusStore;
    }

    public MessageBox findOne(String uid){
        User user = new User();
        user.setUid(uid);
        return new MessageBox(user,folderFinder);
    }
    public List<MessageBox> find(Set<Long> uids){
        return Lists.newArrayList();
    }
}
