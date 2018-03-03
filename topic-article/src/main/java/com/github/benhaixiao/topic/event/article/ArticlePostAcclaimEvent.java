package com.github.benhaixiao.topic.event.article;

import com.github.benhaixiao.topic.event.Event;
import com.github.benhaixiao.topic.acclaim.Acclaim;

/**
 * @author xiaobenhai
 *
 */
public class ArticlePostAcclaimEvent extends Event {

    private String appid;
    private Acclaim acclaim;

    public ArticlePostAcclaimEvent() {
        this.type = Type.ARTICLE_POST_ACCLAIM;
    }

    public ArticlePostAcclaimEvent(String appid,Acclaim acclaim) {
        this.type = Type.ARTICLE_POST_ACCLAIM;
        this.appid = appid;
        this.acclaim = acclaim;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public Acclaim getAcclaim() {
        return acclaim;
    }

    public void setAcclaim(Acclaim acclaim) {
        this.acclaim = acclaim;
    }
}
