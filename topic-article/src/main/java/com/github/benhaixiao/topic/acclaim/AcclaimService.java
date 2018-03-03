package com.github.benhaixiao.topic.acclaim;

import com.github.benhaixiao.topic.article.ArticleAcclaimStore;
import com.github.benhaixiao.topic.context.AppContext;
import com.github.benhaixiao.topic.event.Event;
import com.github.benhaixiao.topic.event.EventManager;
import com.github.benhaixiao.topic.event.article.ArticleEventUtils;
import com.github.benhaixiao.topic.exception.NoSuchAcclaimException;
import com.github.benhaixiao.topic.node.NodeMetaStore;

import java.util.List;

/**
 * @author xiaobenhai
 *
 */
public class AcclaimService {

    private AppContext appContext;
    private ArticleAcclaimStore articleAcclaimStore;
    private NodeMetaStore nodeMetaStore;

    public AcclaimService(AppContext appContext) {
        this.appContext = appContext;
        this.articleAcclaimStore = appContext.getArticleAcclaimStore();
        this.nodeMetaStore = appContext.getNodeMetaStore();
    }

    public long acclaimArticle(String uid,String articleId){
        Acclaim acclaim = new Acclaim(uid,articleId);
        articleAcclaimStore.store(acclaim);
        long acclaimCount = nodeMetaStore.incStatsCount(articleId, "acmCnt");
        //触发事件
        Event event = ArticleEventUtils.createArticleAcclaimEvent(appContext.getApp().getAppid(), acclaim);
        EventManager.raiseEvent(event);

        return acclaimCount;
    }

    public long unAcclaimArticle(String uid,String articleId){
        Acclaim acclaim = new Acclaim(uid,articleId);
        int result = articleAcclaimStore.remove(acclaim);
        long acclaimCount = 0;
        if(result == 1){
            acclaimCount = nodeMetaStore.decStatsCount(articleId,"acmCnt");
            //触发事件
            Event event = ArticleEventUtils.createArticleUnAcclaimEvent(appContext.getApp().getAppid(), acclaim);
            EventManager.raiseEvent(event);
        }
        return acclaimCount;
    }

    public Acclaim getArticleAcclaim(String uid, String articleId)throws NoSuchAcclaimException{
       Acclaim acclaim =articleAcclaimStore.findOne(uid, articleId);
        if (acclaim==null) {
            throw new NoSuchAcclaimException();
        }
        return articleAcclaimStore.findOne(uid,articleId);
    }

    public List<Acclaim> listArticleAcclaimByTargetId(String targetId,Integer offer,Integer limit) throws NoSuchAcclaimException{
        List<Acclaim> acclaims=articleAcclaimStore.listByTargetId(targetId, offer, limit);
        if (acclaims==null){
            throw new NoSuchAcclaimException();
        }
        return acclaims;
    }

    public int getArticleAcclaimCountByTargetId(String articleId) {
        Long count=articleAcclaimStore.getTargetIdCount(articleId);
        return count.intValue();
    }

    public List<Acclaim> listAllArticleAcclaim(Integer offer,Integer limit) throws NoSuchAcclaimException{
        List<Acclaim> acclaims=articleAcclaimStore.listAll(offer, limit);
        if (acclaims==null){
            throw new NoSuchAcclaimException();
        }
        return acclaims;
    }

    public int getTotalCountArticleAcclaim(){
        Long count=articleAcclaimStore.getTotalCount();
        return count.intValue();
    }

}
