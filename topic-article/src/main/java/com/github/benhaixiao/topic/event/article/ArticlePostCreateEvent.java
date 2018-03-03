package com.github.benhaixiao.topic.event.article;

import com.github.benhaixiao.topic.event.Event;
import com.github.benhaixiao.topic.post.Post;

/**
 * @author xiaobenhai
 *
 */
public class ArticlePostCreateEvent extends Event{

    private String appid;
    private String articleId;
    private Post post;

    public ArticlePostCreateEvent(){
        this.type = Type.ARTICLE_POST_CREATE;
    }

    public ArticlePostCreateEvent(String appid,Post post) {
        this.type = Type.ARTICLE_POST_CREATE;
        this.appid = appid;
        this.post = post;
        this.articleId = post.getTargetId();
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

    public Post getPost() {
        return post;
    }
}
