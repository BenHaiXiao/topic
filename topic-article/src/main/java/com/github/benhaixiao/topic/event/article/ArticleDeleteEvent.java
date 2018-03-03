package com.github.benhaixiao.topic.event.article;

import com.github.benhaixiao.topic.article.Article;
import com.github.benhaixiao.topic.event.Event;

/**
 * @author xiaobenhai
 *
 */
public class ArticleDeleteEvent extends Event {
    private String appid;
    private Article article;

    public ArticleDeleteEvent() {
        this.type = Type.ARTICLE_DELETE;
    }
    public ArticleDeleteEvent(String appid,Article article) {
        this.type = Type.ARTICLE_DELETE;
        this.appid = appid;
        this.article = article;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
}
