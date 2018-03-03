package com.github.benhaixiao.topic.event.article;

import com.github.benhaixiao.topic.acclaim.Acclaim;
import com.github.benhaixiao.topic.article.Article;
import com.github.benhaixiao.topic.event.Event;
import com.github.benhaixiao.topic.follow.Follow;
import com.github.benhaixiao.topic.post.Post;

/**
 * @author xiaobenhai
 *
 */
public class ArticleEventUtils {

    public static ArticleCreateEvent createArticleCreateEvent(String appid,Article article){
        return new ArticleCreateEvent(appid,article);
    }

    public static ArticleDeleteEvent createArticleDeleteEvent(String appid,Article article){
        return new ArticleDeleteEvent(appid,article);
    }

    public static ArticleModifyEvent createArticleModifyEvent(String appid,Article oldArticle,Article newArticle){
        return new ArticleModifyEvent(appid,oldArticle,newArticle);
    }

    public static ArticleAcclaimEvent createArticleAcclaimEvent(String appid,Acclaim acclaim){
        return new ArticleAcclaimEvent(appid,acclaim);
    }

    public static ArticleUnAcclaimEvent createArticleUnAcclaimEvent(String appid,Acclaim acclaim){
        return new ArticleUnAcclaimEvent(appid,acclaim);
    }

    public static ArticleFollowEvent createArticleFollowEvent(String appid,Follow follow){
        return new ArticleFollowEvent(appid,follow);
    }

    public static ArticleUnFollowEvent createArticleUnFollowEvent(String appid,Follow follow){
        return new ArticleUnFollowEvent(appid,follow);
    }

    public static ArticlePostCreateEvent createArticlePostCreateEvent(String appid,Post post){
        return new ArticlePostCreateEvent(appid,post);
    }

    public static ArticlePostDeleteEvent createArticlePostDeleteEvent(String appid,Post post){
        return new ArticlePostDeleteEvent(appid,post);
    }

    public static ArticlePostModifyEvent createArticlePostModifyEvent(String appid,Post oldPost,Post newPost){
        return new ArticlePostModifyEvent(appid,oldPost,newPost);
    }

    public static Event createArticlePostAcclaimEvent(String appid, Acclaim acclaim) {
        return new ArticlePostAcclaimEvent(appid,acclaim);
    }

    public static Event createArticlePostUnAcclaimEvent(String appid, Acclaim acclaim) {
        return new ArticlePostUnAcclaimEvent(appid,acclaim);
    }
}
