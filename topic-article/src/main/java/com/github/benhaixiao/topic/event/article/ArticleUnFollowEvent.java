package com.github.benhaixiao.topic.event.article;

import com.github.benhaixiao.topic.event.Event;
import com.github.benhaixiao.topic.follow.Follow;

import java.util.Date;

/**
 * @author xiaobenhai
 *
 */
public class ArticleUnFollowEvent extends Event {

    private String appid;
    private String articleId;
    private String uid;
    private Date time;

    public ArticleUnFollowEvent(){
        this.type = Type.ARTICLE_UNFOLLOW;
    }

    public ArticleUnFollowEvent(String appid,Follow follow){
        this.type = Type.ARTICLE_UNFOLLOW;
        this.appid = appid;
        this.articleId = follow.getTargetId();
        this.uid = follow.getUid();
        this.time = follow.getCreateTime();
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
