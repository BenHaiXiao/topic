package com.github.benhaixiao.topic.message.domain;

import com.github.benhaixiao.topic.encoder.MD5;
import com.github.benhaixiao.topic.message.status.MessageStatus;
import com.google.common.collect.Lists;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * @author xiaobenhai
 *
 */
@Document(collection = "message")
public class Message{
    @Transient
    protected String id;
    @Transient
    protected String folderPath;
    @Field("sender")
    protected User sender;
    @Field("recipients")
    protected List<User> recipients;

    @Field("main")
    protected Body main;
    @Field("refer")
    protected Body refer;
    @Field("source")
    protected Body source;
    @Field("attachments")
    protected List<Attachment> attachments;

    @Field("timestamp")
    protected long timestamp;
    @Field("digest")
    protected String digest;
    @Transient
    private MessageStatus status = MessageStatus.NEW;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFolderPath() {
        return folderPath;
    }

    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public List<User> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<User> recipients) {
        this.recipients = recipients;
    }

    public Body getMain() {
        return main;
    }

    public void setMain(Body main) {
        this.main = main;
    }

    public Body getRefer() {
        return refer;
    }

    public void setRefer(Body refer) {
        this.refer = refer;
    }

    public Body getSource() {
        return source;
    }

    public void setSource(Body source) {
        this.source = source;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public MessageStatus getStatus() {
        return status;
    }

    public void setStatus(MessageStatus status) {
        this.status = status;
    }

    public void addRecipient(User recipient){
        if(recipient == null){
            return;
        }
        if(this.recipients == null){
            this.recipients = Lists.newArrayList();
        }
        this.recipients.add(recipient);
    }

    public void calDigest(){
        StringBuilder msgBuilder = new StringBuilder();
        msgBuilder.append(main.toString());
        msgBuilder.append(folderPath);
        if(refer != null){
            msgBuilder.append(refer.toString());
        }
        if(source  != null){
            msgBuilder.append(source.toString());
        }
        this.digest = MD5.getMD5(msgBuilder.toString());
    }
}
