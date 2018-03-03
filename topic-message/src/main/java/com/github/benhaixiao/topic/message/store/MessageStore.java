package com.github.benhaixiao.topic.message.store;

import com.github.benhaixiao.topic.message.context.MessageContext;
import com.github.benhaixiao.topic.message.domain.Message;
import com.github.benhaixiao.topic.message.status.MessageStatus;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

/**
 * @author xiaobenhai
 *
 */
public class MessageStore{

    private static final Logger LOG = LoggerFactory.getLogger(MessageStore.class);

    private MongoTemplate msgMongoTemplate;

    private String messageCollection = "message";

    public MessageStore(MongoTemplate msgMongoTemplate,String messageCollection){
        this.msgMongoTemplate = msgMongoTemplate;
        this.messageCollection = messageCollection;
    }

    public void store(Message message, MessageContext messageContext) {
        try{
            msgMongoTemplate.save(new MessageDB(message,messageContext.getUser().getUid()), messageCollection);
        }catch (Exception e){
            LOG.error("store message error, message:{} msg:{}",message,e);
        }
    }

    public void delete(Message message, MessageContext messageContext) {
        try{
            msgMongoTemplate.updateFirst(new Query(Criteria.where("_id").is(message.getId())),new Update().set("status", MessageStatus.DELETE), messageCollection);
        }catch (Exception e){
            LOG.error("delete message error. message:{} msg:{}",message,e);
        }
    }

    public Message findOne(String messageId,MessageContext messageContext) {
        MessageDB messageDB = msgMongoTemplate.findOne(new Query(Criteria.where("uid").is(messageContext.getUser().getUid()).and("_id").is(messageId)),MessageDB.class, messageCollection);
        return messageDB.toMessage();
    }

    public List<Message> findByFolderPath(String folderPath, MessageContext messageContext,int offset, int limit) {
        try{
            List<MessageDB> messageDBs = msgMongoTemplate.find(new Query(Criteria.where("uid").is(messageContext.getUser().getUid()).and("folder").is(folderPath)),MessageDB.class, messageCollection);
            if(messageDBs == null){
                return Lists.newArrayList();
            }
            List<Message> messages = Lists.newArrayList();
            for(MessageDB messageDB : messageDBs){
                messages.add(messageDB.toMessage());
            }
            return messages;
        }catch (Exception e){
            LOG.warn("find message by folder path error. folderPath:{} messageContext:{} offset:{} limit:{} msg:{}",folderPath,messageContext,offset,limit,e);
        }
        return Lists.newArrayList();
    }

    public static class MessageDB{
        @Id
        private String id;
        @Field("uid")
        private String uid;
        @Field("folder")
        private String folderPath;
        @Field("message")
        private Message message;
        @Field("status")
        private MessageStatus status;

        public MessageDB() {
        }

        public MessageDB(Message message,String uid) {
            message.calDigest();
            setUid(uid);
            setFolderPath(message.getFolderPath());
            setStatus(MessageStatus.NEW);
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getFolderPath() {
            return folderPath;
        }

        public void setFolderPath(String folderPath) {
            this.folderPath = folderPath;
        }

        public Message getMessage() {
            return message;
        }

        public void setMessage(Message message) {
            this.message = message;
        }

        public MessageStatus getStatus() {
            return status;
        }

        public void setStatus(MessageStatus status) {
            this.status = status;
        }

        public Message toMessage(){
            message.setId(getId());
            message.setFolderPath(getFolderPath());
            message.setStatus(getStatus());
            return message;
        }
    }
}
