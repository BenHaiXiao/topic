package com.github.benhaixiao.topic.user;

import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author xiaobenhai
 *
 */
public class User{
    @Field("uid")
    private String uid;
    @Field("nick")
    private String nick;
    @Field("avatar")
    private String avatar;

    public User() {
    }

    public User(String uid,String nick,String avatar){
        this.uid = uid;
        this.avatar = avatar;
        this.nick = nick;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
