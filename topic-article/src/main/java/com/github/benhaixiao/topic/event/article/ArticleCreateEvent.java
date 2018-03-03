package com.github.benhaixiao.topic.event.article;

import com.github.benhaixiao.topic.article.Article;
import com.github.benhaixiao.topic.event.Event;

/**
 * @author xiaobenhai
 *
 */
public class ArticleCreateEvent extends Event {

    private String appid;
    private Article article;

    public ArticleCreateEvent(){
        this.type = Type.ARTICLE_CREATE;
    }

    public ArticleCreateEvent(String appid,Article article) {
        this.type = Type.ARTICLE_CREATE;
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
