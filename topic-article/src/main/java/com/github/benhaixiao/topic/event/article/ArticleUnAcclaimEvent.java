package com.github.benhaixiao.topic.event.article;

import com.github.benhaixiao.topic.acclaim.Acclaim;
import com.github.benhaixiao.topic.event.Event;

import java.util.Date;

/**
 * @author xiaobenhai
 *
 */
public class ArticleUnAcclaimEvent extends Event{

    private String appid;
    private String articleId;
    private String uid;
    private Date time;

    public ArticleUnAcclaimEvent() {
        this.type = Type.ARTICLE_UNACCLAIM;
    }

    public ArticleUnAcclaimEvent(String appid,Acclaim acclaim){
        this.type = Type.ARTICLE_UNACCLAIM;
        this.appid = appid;
        this.articleId = acclaim.getTargetId();
        this.uid = acclaim.getUid();
        this.time = acclaim.getCreateTime();
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
