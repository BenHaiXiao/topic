package com.github.benhaixiao.topic.message.box;


import com.github.benhaixiao.topic.message.folder.Folder;
import com.github.benhaixiao.topic.message.folder.FolderFinder;
import com.github.benhaixiao.topic.message.context.MessageContext;
import com.github.benhaixiao.topic.message.domain.Message;
import com.github.benhaixiao.topic.message.domain.User;

import java.util.List;

/**
 * @author xiaobenhai
 *
 */
public class MessageBox {
    private FolderFinder folderFinder;
    private MessageBoxStatus status;
    private MessageContext messageContext = new MessageContext();

    public MessageBox(User user,FolderFinder folderFinder){
        this.folderFinder = folderFinder;
        messageContext.setUser(user);
    }

    public void put(Message message){
        String folderPath = message.getFolderPath();
        if(folderPath == null){
            return;
        }
        Folder folder = folderFinder.findOne(folderPath);
        folder.put(message, messageContext);
    }

    public void delete(Message message){
        String folderPath = message.getFolderPath();
        if(folderPath == null){
            return;
        }
        Folder folder = folderFinder.findOne(folderPath);
        folder.delete(message, messageContext);
    }

    public List<? extends Message> find(String folderPath,int offset,int limit){
        Folder folder = folderFinder.findOne(folderPath);
        return folder.find(messageContext,offset,limit);
    }

    public MessageBoxStatus getStatus(){
        return status;
    }
}
