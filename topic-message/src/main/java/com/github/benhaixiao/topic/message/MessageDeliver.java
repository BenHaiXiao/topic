package com.github.benhaixiao.topic.message;

import com.google.common.collect.Lists;
import com.github.benhaixiao.topic.message.box.MessageBox;
import com.github.benhaixiao.topic.message.box.MessageBoxFinder;
import com.github.benhaixiao.topic.message.domain.Message;
import com.github.benhaixiao.topic.message.domain.User;

import java.util.List;

/**
 * @author xiaobenhai
 *
 */

public class MessageDeliver {

    private MessageBoxFinder messageBoxFinder;

    public MessageDeliver(MessageBoxFinder messageBoxFinder){
        this.messageBoxFinder = messageBoxFinder;
    }

    public MessageBox findMessageBox(User user){
        return messageBoxFinder.findOne(user.getUid());
    }

    public List<MessageBox> findMessageBoxes(List<User> users){
        List<MessageBox> messageBoxes = Lists.newArrayList();
        for(User user : users) {
            MessageBox messageBox = messageBoxFinder.findOne(user.getUid());
            messageBoxes.add(messageBox);
        }
        return messageBoxes;
    }

    public void deliver(Message message){
        List<User> users = message.getRecipients();
        if(users == null){
            return;
        }

        List<MessageBox> messageBoxes = findMessageBoxes(users);
        for(MessageBox messageBox : messageBoxes){
            messageBox.put(message);
        }
    }

    public void delete(Message message){
        List<User> users = message.getRecipients();
        if(users == null){
            return;
        }
        List<MessageBox> messageBoxes = findMessageBoxes(users);
        for(MessageBox messageBox : messageBoxes){
            messageBox.delete(message);
        }
    }
}
