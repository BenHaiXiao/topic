package com.github.benhaixiao.topic.event.article;

import com.github.benhaixiao.topic.event.Event;
import com.github.benhaixiao.topic.post.Post;

/**
 * @author xiaobenhai
 *
 */
public class ArticlePostModifyEvent extends Event {

    private String appid;
    private String articleId;
    private Post oldPost;
    private Post newPost;

    public ArticlePostModifyEvent(){
        this.type = Type.ARTICLE_POST_MODIFY;
    }

    public ArticlePostModifyEvent(String appid,Post oldPost, Post newPost) {
        this.type = Type.ARTICLE_POST_MODIFY;
        this.appid = appid;
        this.oldPost = oldPost;
        this.newPost = newPost;
        this.articleId = oldPost.getTargetId();
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

    public Post getOldPost() {
        return oldPost;
    }

    public Post getNewPost() {
        return newPost;
    }
}
