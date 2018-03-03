package com.github.benhaixiao.topic.post;

import com.github.benhaixiao.topic.context.AppContext;
import com.github.benhaixiao.topic.event.Event;
import com.github.benhaixiao.topic.event.EventManager;
import com.github.benhaixiao.topic.event.article.ArticleEventUtils;
import com.github.benhaixiao.topic.exception.NoSuchAcclaimException;
import com.github.benhaixiao.topic.exception.NoSuchPostException;
import com.github.benhaixiao.topic.node.NodeMetaStore;
import com.github.benhaixiao.topic.acclaim.Acclaim;

import java.util.List;

/**
 * @author xiaobenhai
 *
 */
public class PostService {

    private AppContext appContext;
    private PostStore postStore;
    private NodeMetaStore nodeMetaStore;
    private PostAcclaimStore postAcclaimStore;

    public PostService(AppContext appContext){
        this.appContext = appContext;
        this.postStore = appContext.getPostStore();
        this.nodeMetaStore = appContext.getNodeMetaStore();
        this.postAcclaimStore = appContext.getPostAcclaimStore();
    }

    public void postArticle(Post post,String articleId){
        postStore.store(post);
        nodeMetaStore.incStatsCount(articleId,"potCnt");
        //触发事件
        Event event = ArticleEventUtils.createArticlePostCreateEvent(appContext.getApp().getAppid(),post);
        EventManager.raiseEvent(event);
    }

    public void unPostArticle(String postId,String articleId) throws NoSuchPostException {
        Post post = postStore.findOne(postId);
        if(post == null){
            throw new NoSuchPostException();
        }
        postStore.remove(postId);
        nodeMetaStore.decStatsCount(articleId,"potCnt");
        //触发事件
        Event event = ArticleEventUtils.createArticlePostDeleteEvent(appContext.getApp().getAppid(),post);
        EventManager.raiseEvent(event);
    }

    public List<Post> findAncestors(List<Post> posts) {
        return postStore.findAncestors(posts);
    }

    public List<Post> find(String articleId, int offset, int limit, String orderBy) {
        return postStore.find(articleId,offset,limit,orderBy);
    }

    public long acclaimPost(String uid,String postId){
        Acclaim acclaim = new Acclaim(uid,postId);
        postAcclaimStore.store(acclaim);
        long acclaimCount = postStore.incAcclaimCount(postId);
        //触发事件
        Event event = ArticleEventUtils.createArticlePostAcclaimEvent(appContext.getApp().getAppid(), acclaim);
        EventManager.raiseEvent(event);

        return acclaimCount;
    }

    public long unAcclaimPost(String uid,String postId){
        Acclaim acclaim = new Acclaim(uid,postId);
        int result = postAcclaimStore.remove(acclaim);
        long acclaimCount = 0;
        if(result == 1){
            acclaimCount = postStore.decAcclaimCount(postId);
            //触发事件
            Event event = ArticleEventUtils.createArticlePostUnAcclaimEvent(appContext.getApp().getAppid(), acclaim);
            EventManager.raiseEvent(event);
        }
        //TODO result 其他情况
        return acclaimCount;
    }

    public Acclaim getPostAcclaim(String uid,String postId)throws NoSuchAcclaimException {
        Acclaim acclaim =postAcclaimStore.findOne(uid,postId);
        if (acclaim==null) {
            throw new NoSuchAcclaimException();
        }
        return acclaim;
    }
    /**
     * 根据点赞对象Id获取所有评论点赞：起点offer，limit条记录
     * @author xiaobenhai
     * @return List<Acclaim>
     */
    public List<Acclaim> listPostAcclaimByTargetId(String targetId,Integer offer,Integer limit) throws NoSuchAcclaimException{
        List<Acclaim> acclaims=postAcclaimStore.listByTargetId(targetId, offer, limit);
        if (acclaims==null){
            throw new NoSuchAcclaimException();
        }
        return acclaims;
    }
    /**
     * 获取点赞对象的点赞总数
     * @author QuPeng
     * @param articleId
     * @return int
     */
    public int getPostAcclaimCountByTargetId(String articleId) {
        Long count=postAcclaimStore.getTargetIdCount(articleId);
        return count.intValue();
    }
    /**
     * 获取所有评论点赞：起点offer，limit条记录
     * @author xiaobenhai
     * @return List<Acclaim>
     */
    public List<Acclaim> listAllPostAcclaim(Integer offer,Integer limit) throws NoSuchAcclaimException{
        List<Acclaim> acclaims=postAcclaimStore.listAll(offer, limit);
        if (acclaims==null){
            throw new NoSuchAcclaimException();
        }
        return acclaims;
    }
    /**
     * 获取所有文章点赞数量
     * @author xiaobenhai
     * @return int
     */
    public int getTotalCountPostAcclaim(){
        Long count=postAcclaimStore.getTotalCount();
        return count.intValue();
    }
}
