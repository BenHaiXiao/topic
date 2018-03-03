package com.github.benhaixiao.topic.event.article;

import com.github.benhaixiao.topic.post.Post;
import com.github.benhaixiao.topic.event.Event;

/**
 * @author xiaobenhai
 *
 */
public class ArticlePostDeleteEvent extends Event{

    private String appid;
    private String articleId;
    private Post post;

    public ArticlePostDeleteEvent(){
        this.type = Type.ARTICLE_POST_DELETE;
    }

    public ArticlePostDeleteEvent(String appid,Post post) {
        this.type = Type.ARTICLE_POST_DELETE;
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
