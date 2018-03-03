package com.github.benhaixiao.topic.event.article;

import com.github.benhaixiao.topic.article.Article;
import com.github.benhaixiao.topic.event.Event;

/**
 * @author xiaobenhai
 *
 */
public class ArticleModifyEvent extends Event {

    private String appid;
    private Article oldArticle;
    private Article newArticle;

    public ArticleModifyEvent(){
        this.type = Type.ARTICLE_MODIFY;
    }

    public ArticleModifyEvent(String appid,Article oldArticle, Article newArticle) {
        this.type = Type.ARTICLE_MODIFY;
        this.appid = appid;
        this.oldArticle = oldArticle;
        this.newArticle = newArticle;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public Article getOldArticle() {
        return oldArticle;
    }

    public void setOldArticle(Article oldArticle) {
        this.oldArticle = oldArticle;
    }

    public Article getNewArticle() {
        return newArticle;
    }

    public void setNewArticle(Article newArticle) {
        this.newArticle = newArticle;
    }
}
