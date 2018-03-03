package com.github.benhaixiao.topic.message.box;

import com.github.benhaixiao.topic.message.context.MessageContext;
import com.github.benhaixiao.topic.message.domain.Message;
import com.github.benhaixiao.topic.message.status.MessageStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

/**
 * @author xiaobenhai
 *
 */
public class MessageBoxStatusStore {

    /**
     * {
     *     uid:xxxxxxx
     *     totalMsg:100
     *     newMsg:200
     *     notice:{
     *         totalMsg:100
     *         newMsg:200
     *     }
     *     reply:{
     *         totalMsg:100
     *         mewMsg:200
     *     }
     * }
     */


    private static final Logger LOG = LoggerFactory.getLogger(MessageBoxStatusStore.class);

    private MongoTemplate msgMongoTemplate;

    public MessageBoxStatusStore(MongoTemplate msgMongoTemplate){
        this.msgMongoTemplate = msgMongoTemplate;
    }

    public void incMessageNum(Message message,MessageContext messageContext){
        String uid = messageContext.getUser().getUid();
        if(uid == null){
            return;
        }
        try {
        }catch (Exception e){
            LOG.warn("increase message box number error.message:{}, messageContext:{} msg:{}",message,messageContext,e);
        }
    }
    public void decMessageNum(Message message,MessageContext messageContext){
        String uid = messageContext.getUser().getUid();
        if(uid == null){
            return;
        }
        try {
            Update update = new Update();
            update.inc("totalMsg",-1).inc(message.getFolderPath() + ".totalMsg",-1);
            if(message.getStatus() == MessageStatus.NEW){
                update.inc("newMsg", -1);
                update.inc(message.getFolderPath() + ".newMsg", -1);
            }
            msgMongoTemplate.updateFirst(new Query(Criteria.where("_id").is(uid)),update, MessageBoxStatus.class);
        }catch (Exception e){
            LOG.warn("decrease message box number error.message:{}, messageContext:{} msg:{}",message,messageContext,e);
        }
    }

    public void batchMarkMessageToDelete(List<Message> messages,MessageContext messageContext){

    }

    public void markAllMessageReaded(String folderPath,MessageContext messageContext){
        String uid = messageContext.getUser().getUid();
        if(uid == null){
            return;
        }
        msgMongoTemplate.updateMulti(new Query(Criteria.where("_id").is(uid).and("folder").is(folderPath).and("status").is(MessageStatus.NEW)),new Update().set("status",MessageStatus.READED),Message.class);
        msgMongoTemplate.updateFirst(new Query(Criteria.where("_id").is(uid)),new Update().set("newMsg",0).set(folderPath + ".newMsg",0),MessageBoxStatus.class);
    }

    public MessageBoxStatus getStatus(MessageContext messageContext){
        String uid = messageContext.getUser().getUid();
        if(uid == null){
            return new MessageBoxStatus();
        }
        MessageBoxStatus status = msgMongoTemplate.findOne(new Query(Criteria.where("_id").is(uid)),MessageBoxStatus.class);
        if(status == null){
            return new MessageBoxStatus();
        }
        return status;
    }
}
