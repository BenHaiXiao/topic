package com.github.benhaixiao.topic.user;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

/**
 * @author xiaobenhai
 *
 */
public class UserStore {

    private MongoTemplate userMongoTemplate;

    public UserStore(MongoTemplate userMongoTemplate){
        this.userMongoTemplate = userMongoTemplate;
    }

    public User findOne(String uid){
        User user = new User();
        user.setUid(uid);
        user.setNick(null);
        user.setAvatar(null);
        return user;
    }

    public List<User> find(List<String> uids) {
        return userMongoTemplate.find(new Query(Criteria.where("uid").in(uids)), User.class);
    }
}
