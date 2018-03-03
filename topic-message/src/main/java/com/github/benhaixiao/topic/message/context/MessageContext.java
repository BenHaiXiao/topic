package com.github.benhaixiao.topic.message.context;

import com.github.benhaixiao.topic.message.domain.User;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * @author xiaobenhai
 *
 */
public class MessageContext {
    private User user;
    private MongoTemplate msgMongoTemplate;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public MongoTemplate getMsgMongoTemplate() {
        return msgMongoTemplate;
    }

    public void setMsgMongoTemplate(MongoTemplate msgMongoTemplate) {
        this.msgMongoTemplate = msgMongoTemplate;
    }
}
