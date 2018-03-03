package com.github.benhaixiao.topic.event.article;

import com.github.benhaixiao.topic.acclaim.Acclaim;
import com.github.benhaixiao.topic.event.Event;

/**
 * @author xiaobenhai
 *
 */
public class ArticlePostUnAcclaimEvent extends Event {

    private String appid;
    private Acclaim acclaim;

    public ArticlePostUnAcclaimEvent() {
        this.type = Type.ARTICLE_POST_UNACCLAIM;
    }

    public ArticlePostUnAcclaimEvent(String appid, Acclaim acclaim) {
        this.type = Type.ARTICLE_POST_UNACCLAIM;
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
