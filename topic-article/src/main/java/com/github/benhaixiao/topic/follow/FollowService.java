package com.github.benhaixiao.topic.follow;

import com.github.benhaixiao.topic.article.Article;
import com.github.benhaixiao.topic.article.ArticleFollowStore;
import com.github.benhaixiao.topic.context.AppContext;
import com.github.benhaixiao.topic.event.Event;
import com.github.benhaixiao.topic.event.EventManager;
import com.github.benhaixiao.topic.event.article.ArticleEventUtils;
import com.github.benhaixiao.topic.event.folder.FolderEventUtils;
import com.github.benhaixiao.topic.exception.DuplicateException;
import com.github.benhaixiao.topic.exception.NoSuchFollowException;
import com.github.benhaixiao.topic.folder.Folder;
import com.github.benhaixiao.topic.node.NodeMeta;
import com.github.benhaixiao.topic.node.NodeMetaStore;
import com.github.benhaixiao.topic.folder.FolderFollowStore;

import java.util.List;
import java.util.ArrayList;

/**
 * @author xiaobenhai
 *
 */
public class FollowService {

    private AppContext appContext;
    private FolderFollowStore folderFollowStore;
    private ArticleFollowStore articleFollowStore;
    private NodeMetaStore nodeMetaStore;

    public FollowService(AppContext appContext) {
        this.appContext = appContext;
        this.folderFollowStore = appContext.getFolderFollowStore();
        this.articleFollowStore=appContext.getArticleFollowStore();
        this.nodeMetaStore = appContext.getNodeMetaStore();
    }

    public long followFolder(String uid,Folder folder) throws DuplicateException {
        Follow follow = new Follow(uid,folder.getNodeId());
        folderFollowStore.store(follow);
        long followCount = nodeMetaStore.incStatsCount(folder.getNodeId(),"fowCnt");
        //触发事件
        Event event = FolderEventUtils.createFolderFollowEvent(appContext.getApp().getAppid(), follow);
        EventManager.raiseEvent(event);
        return followCount;
    }

    /**
     * xiaobenhai
     * @param follow
     * @return
     * @throws DuplicateException
     */
    public long followFolder(Follow follow) throws DuplicateException{
        folderFollowStore.store(follow);
        long followCount = nodeMetaStore.incStatsCount(follow.getTargetId(),"fowCnt");
        //触发事件
        Event event = FolderEventUtils.createFolderFollowEvent(appContext.getApp().getAppid(), follow);
        EventManager.raiseEvent(event);
        return followCount;
    }


    public long followArticle(String uid,Article article) throws DuplicateException{
        Follow follow = new Follow(uid,article.getNodeId());
        articleFollowStore.store(follow);
        long followCount = nodeMetaStore.incStatsCount(article.getNodeId(), "fowCnt");
        //触发事件
        Event event = ArticleEventUtils.createArticleFollowEvent(appContext.getApp().getAppid(), follow);
        EventManager.raiseEvent(event);
        return followCount;
    }

    /**
     * xiaobenhai
     * @param follow
     * @return
     * @throws DuplicateException
     */
    public long followArticle(Follow follow) throws DuplicateException{
        articleFollowStore.store(follow);
        long followCount = nodeMetaStore.incStatsCount(follow.getTargetId(), "fowCnt");
        //触发事件
        Event event = ArticleEventUtils.createArticleFollowEvent(appContext.getApp().getAppid(), follow);
        EventManager.raiseEvent(event);
        return followCount;
    }
    public long unFollowFolder(String uid,Folder folder){
        NodeMeta folderNodeMeta = folder.getNodeMeta();
        Follow follow = new Follow(uid,folder.getNodeId());
        int result = folderFollowStore.remove(follow);
        long followCount = 0;
        if(result == 1){
            followCount = nodeMetaStore.decStatsCount(folderNodeMeta.getId(),"fowCnt");
            //触发事件
            Event event = FolderEventUtils.createFolderUnFollowEvent(appContext.getApp().getAppid(), follow);
            EventManager.raiseEvent(event);
        }
        //TODO result 其他情况
        return followCount;
    }

    /**
     * xiaobenhai
     * @param follow
     * @return
     */
    public long unFollowFolder(Follow follow){
        int result = folderFollowStore.remove(follow);
        long followCount = 0;
        if(result == 1){
            followCount = nodeMetaStore.decStatsCount(follow.getTargetId(),"fowCnt");
            //触发事件
            Event event = FolderEventUtils.createFolderUnFollowEvent(appContext.getApp().getAppid(), follow);
            EventManager.raiseEvent(event);
        }
        //TODO result 其他情况
        return followCount;
    }

    /**
     * xiaobenhai
     * @param follow
     * @return
     */
    public long unFollowArticle(Follow follow){
        int result=articleFollowStore.remove(follow);
        long followCount=0;
        if (result==1){
            followCount=nodeMetaStore.decStatsCount(follow.getTargetId(),"fowCnt");
            //触发事件
            Event event=ArticleEventUtils.createArticleUnFollowEvent(appContext.getApp().getAppid(), follow);
            EventManager.raiseEvent(event);
        }
        return followCount;
    }

    public Follow getFollowFolder(String uid,Folder folder){
        return folderFollowStore.findOne(uid,folder.getNodeId());

    }
    /**
     * 根据目录关注对象Id获取所有关注：起点offer，limit条记录
     * @author xiaobenhai
     * @return List<Follow>
     */
    public List<Follow> listFolderFollowByTargetId(String targetId,Integer offer,Integer limit) throws NoSuchFollowException {
        List<Follow> follows=folderFollowStore.listByTargetId(targetId, offer, limit);
        if (follows==null){
            throw new NoSuchFollowException();
        }
        return follows;
    }
    /**
     * 获取目录关注对象的关注总数
     * @author xiaobenhai
     * @param targetId
     * @return int
     */
    public int getFolderFollowCountByTargetId(String targetId) {
        Long count=folderFollowStore.getTargetIdCount(targetId);
        return count.intValue();
    }
    /**
     * 获取目录所有关注：起点offer，limit条记录
     * @author xiaobenhai
     * @return List<Follow>
     */
    public List<Follow> listAllFolderFollow(Integer offer,Integer limit) throws NoSuchFollowException{
        List<Follow> follows=folderFollowStore.listAll(offer, limit);
        if (follows==null){
            throw new NoSuchFollowException();
        }
        return follows;
    }
    /**
     * 获取目录文章总数
     * @author xiaobenhai
     * @return int
     */
    public int getTotalCountFolderFollow(){
        Long count=folderFollowStore.getTotalCount();
        return count.intValue();
    }
    /**
     * 根据文章关注对象Id获取所有关注：起点offer，limit条记录
     * @author xiaobenhai
     * @return List<Follow>
     */
    public List<Follow> listArtcileFollowByTargetId(String targetId,Integer offer,Integer limit) throws NoSuchFollowException {
        List<Follow> follows=articleFollowStore.listByTargetId(targetId, offer, limit);
        if (follows==null){
            throw new NoSuchFollowException();
        }
        return follows;
    }
    /**
     * 获取文章关注对象的关注总数
     * @author xiaobenhai
     * @param targetId
     * @return int
     */
    public int getArtcileFollowCountByTargetId(String targetId) {
        Long count=articleFollowStore.getTargetIdCount(targetId);
        return count.intValue();
    }
    /**
     * 获取文章所有关注：起点offer，limit条记录
     * @author xiaobenhai
     * @return List<Follow>
     */
    public List<Follow> listAllArtcileFollow(Integer offer,Integer limit) throws NoSuchFollowException{
        List<Follow> follows=articleFollowStore.listAll(offer, limit);
        if (follows==null){
            throw new NoSuchFollowException();
        }
        return follows;
    }
    /**
     * 获取w文章关注总数
     * @author xiaobenhai
     * @return int
     */
    public int getTotalCountArtcileFollow(){
        Long count=articleFollowStore.getTotalCount();
        return count.intValue();
    }
    /**
     * 获取关注该目录的用户全部uid
     * @author QuPeng
     * @param folder
     * @return List<String>
     */
    public List<String> getFolderFlwUids(Folder folder) {
        List<Follow> list =  folderFollowStore.find(folder.getNodeId());
        List<String> uids = new ArrayList<String>();
        for(Follow f : list){
            uids.add(f.getUid());
        }
        return uids;
    }




}
