package com.github.benhaixiao.topic.message.folder;


import com.github.benhaixiao.topic.message.context.MessageContext;
import com.github.benhaixiao.topic.message.domain.Message;
import com.github.benhaixiao.topic.message.store.MessageStore;

import java.util.List;

/**
 * @author xiaobenhai
 *
 */
public class Folder {
    private String path;
    private MessageStore messageStore;
    private FolderStatus status;

    public Folder(String path,MessageStore messageStore){
        this.path = path;
        this.messageStore = messageStore;
    }

    public void put(Message message,MessageContext messageContext){
        messageStore.store(message, messageContext);
    }

    public void delete(Message message,MessageContext messageContext){
        messageStore.delete(message,messageContext);
    }
    public String getPath() {
        return path;
    }

    public List<Message> find(MessageContext messageContext,int offset,int limit){
        return messageStore.findByFolderPath(path,messageContext,offset,limit);
    }
}
