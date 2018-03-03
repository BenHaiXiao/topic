package com.github.benhaixiao.topic.folder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.benhaixiao.topic.context.AppContext;
import com.github.benhaixiao.topic.domain.Thumbs;
import com.github.benhaixiao.topic.event.EventManager;
import com.github.benhaixiao.topic.event.article.ArticleEventUtils;
import com.github.benhaixiao.topic.exception.NoSuchArticleException;
import com.github.benhaixiao.topic.exception.NoSuchFolderException;
import com.github.benhaixiao.topic.node.NodeMetaUtils;
import com.google.common.collect.Lists;
import com.github.benhaixiao.topic.article.Article;
import com.github.benhaixiao.topic.article.ArticleStore;
import com.github.benhaixiao.topic.domain.Marks;
import com.github.benhaixiao.topic.event.Event;
import com.github.benhaixiao.topic.event.folder.FolderEventUtils;
import com.github.benhaixiao.topic.exception.NoSuchNodeMetaException;
import com.github.benhaixiao.topic.node.NodeMeta;
import com.github.benhaixiao.topic.node.NodeMetaStore;
import com.github.benhaixiao.topic.node.NodeType;
import com.github.benhaixiao.topic.shared.PathUtils;
import com.github.benhaixiao.topic.user.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author xiaobenhai
 *
 */
@Document(collection = "folder")
public class Folder {
    @Id
    private String nodeId;
    @Field("path")
    private String path;
    @Field("marks")
    private Marks marks;
    @Field("thumbs")
    private Thumbs thumbs;
    @Field("name")
    private String name;
    @Field("icon")
    private String icon;
    @JsonProperty("desc")
    @Field("desc")
    private String description;
    @Field("exts")
    private Map<String,String> exts;
    @Field("creator")
    private User creator;
    @Field("ctime")
    private Date createTime;
    @Field("utime")
    private Date updateTime;
    @JsonIgnore
    @Field("valid")
    private int valid;
    @JsonIgnore
    @Transient
    private FolderStats stats;
    @JsonIgnore
    @Transient
    private AppContext appContext;
    @JsonIgnore
    @Transient
    private FolderFinder folderFinder;
    @JsonIgnore
    @Transient
    private ArticleStore articleStore;
    @JsonIgnore
    @Transient
    private NodeMetaStore nodeMetaStore;
    @JsonIgnore
    @Transient
    private FolderStore folderStore;
    @JsonIgnore
    @Transient
    private FolderFollowStore folderFollowStore;

    public Folder(){

    }

    public Folder(AppContext appContext,String path){
        setAppContext(appContext);
        this.path = path;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Marks getMarks() {
        return marks;
    }

    public void setMarks(Marks marks) {
        this.marks = marks;
    }

    public Thumbs getThumbs() {
        return thumbs;
    }

    public void setThumbs(Thumbs thumbs) {
        this.thumbs = thumbs;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Map<String, String> getExts() {
        return exts;
    }

    public void setExts(Map<String, String> exts) {
        this.exts = exts;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getValid() {
        return valid;
    }

    public void setValid(int valid) {
        this.valid = valid;
    }

    @JsonIgnore
    public NodeMeta getNodeMeta() {
        NodeMeta nodeMeta = nodeMetaStore.findOne(nodeId);
        if(nodeMeta == null){
            throw new NoSuchNodeMetaException();
        }
        return nodeMeta;
    }

    public FolderStats getStats() {
        stats = appContext.getSlothMongoTemplate().findOne(new Query(Criteria.where("folder").is(path)),FolderStats.class);
        if(stats == null){
            stats = new FolderStats();
        }
        return stats;
    }

    public void setStats(FolderStats stats) {
        this.stats = stats;
    }

    public void setAppContext(AppContext appContext){
        this.appContext = appContext;
        this.nodeMetaStore = appContext.getNodeMetaStore();
        this.articleStore = appContext.getArticleStore();
        this.folderFinder = appContext.getFolderFinder();
        this.folderStore = appContext.getFolderStore();
        this.folderFollowStore = appContext.getFolderFollowStore();
    }

    public AppContext getAppContext(){
        return this.appContext;
    }

    public FolderFinder getFolderFinder() {
        return folderFinder;
    }

    public void setFolderFinder(FolderFinder folderFinder) {
        this.folderFinder = folderFinder;
    }

    public ArticleStore getArticleStore() {
        return articleStore;
    }

    public void setArticleStore(ArticleStore articleStore) {
        this.articleStore = articleStore;
    }

    private void incArticleCount(){
        appContext.getSlothMongoTemplate().upsert(new Query(Criteria.where("folder").is(path)),new Update().inc("tocCnt",1),FolderStats.class);
        appContext.getApp().incTopicCount();
        //TODO 持久化
    }

    private void decArticleCount(){
        appContext.getSlothMongoTemplate().updateFirst(new Query(Criteria.where("folder").is(path)), new Update().inc("tocCnt", -1), FolderStats.class);
        appContext.getApp().decTopicCount();
    }

    private void incFolderCount(){
        appContext.getSlothMongoTemplate().upsert(new Query(Criteria.where("folder").is(path)), new Update().inc("fdrCnt", 1), FolderStats.class);
        appContext.getApp().incFolderCount();
    }

    private void decFolderCount(){
        appContext.getSlothMongoTemplate().upsert(new Query(Criteria.where("folder").is(path)), new Update().inc("fdrCnt", -1), FolderStats.class);
        appContext.getApp().decFolderCount();
    }

    public Article storeArticle(Article article){
        //TODO 事务
        NodeMeta nodeMeta = NodeMetaUtils.createNodeMeta(article);
        nodeMetaStore.save(nodeMeta);
        article.setNodeId(nodeMeta.getId());
        article = articleStore.store(article);
        this.incArticleCount();
        //触发事件
        Event event = ArticleEventUtils.createArticleCreateEvent(appContext.getApp().getAppid(),article);
        EventManager.raiseEvent(event);
        return article;
    }

    public void removeArticle(String articleId){
        Article article = getArticle(articleId);
        articleStore.remove(article);
        this.decArticleCount();
        //触发事件
        Event event = ArticleEventUtils.createArticleDeleteEvent(appContext.getApp().getAppid(), article);
        EventManager.raiseEvent(event);
    }

    public Folder storeFolder(Folder folder){
        NodeMeta nodeMeta = NodeMetaUtils.createNodeMeta(folder);
        nodeMetaStore.save(nodeMeta);
        folder.setNodeId(nodeMeta.getId());
        folder.setPath(PathUtils.buildFolderPath(this.path, nodeMeta.getId()));
        folderStore.save(folder);
        this.incFolderCount();
        //触发事件
        Event event = FolderEventUtils.createFolderCreateEvent(appContext.getApp().getAppid(), folder);
        EventManager.raiseEvent(event);
        return folder;
    }

    /**
     * 注意：删除目录时只能通过根目录进行删除，流程：获取根目录－＞调用removeFolder方法删除
     * @param folderPath
     *
     *
     */
    public void removeFolder(String folderPath){
        Folder folder = getFolder(folderPath);

        folderStore.remove(folder);
        //触发事件
        Event event = FolderEventUtils.createFolderDeleteEvent(appContext.getApp().getAppid(), folder);
        EventManager.raiseEvent(event);
    }

    public Article getArticle(String articleId) throws NoSuchArticleException {
        Article article = articleStore.findOne(articleId);
        if(article == null){
            throw new NoSuchArticleException();
        }
        return article;
    }

    public Folder getFolder(String path) throws NoSuchFolderException {
        Folder folder = folderStore.findOne(path);
        if(folder == null){
            throw new NoSuchFolderException();
        }
        return folder;
    }

    public List<Folder> listSubFolder(int offset,int limit){
        List<NodeMeta> nodeMetas = listNodeMeta(NodeType.DIR,offset,limit,null);
        List<String> nodeIds = Lists.newArrayList();
        for(NodeMeta nodeMeta : nodeMetas){
            nodeIds.add(nodeMeta.getId());
        }
        Map<String, Folder> folderMap = folderStore.findByNodeIds(nodeIds);
        return new ArrayList<Folder>(folderMap.values());
    }
    public List<Folder> listSubFolder(){
        List<NodeMeta> nodeMetas = listNodeMeta(NodeType.DIR,null);
        List<String> nodeIds = Lists.newArrayList();
        for(NodeMeta nodeMeta : nodeMetas){
            nodeIds.add(nodeMeta.getId());
        }
        Map<String, Folder> folderMap = folderStore.findByNodeIds(nodeIds);
        return new ArrayList<Folder>(folderMap.values());
    }

    /**
     * @author xiaobenhai
     * @param offset
     * @param limit
     * @return List<Folder>
     */
    public List<Folder> findFolderAll(int offset ,int limit){
        Map<String ,Folder> folderMap =folderStore.findFolderAll(offset, limit);
        return new ArrayList<Folder>(folderMap.values());
    }
    /**
     * @author xiaobenhai
     * @return List<Folder>
     */
    public List<Folder> findFolderAll(){
        Map<String ,Folder> folderMap =folderStore.findFolderAll();
        return new ArrayList<Folder>(folderMap.values());
    }
    /**
     * @author xiaobenhai
     * @param offset
     * @param limit
     * @return List<Article>
     */
    public List<Article> findArticleAll(int offset,int limit){
        Map<String,Article> articleMap = articleStore.findArticleAll(offset, limit);
        return new ArrayList<>(articleMap.values());
    }
    /**
     * @author xiaobenhai
     * @return List<Article>
     */
    public List<Article> findArticleAll(){
        Map<String,Article> articleMap = articleStore.findArticleAll();
        return new ArrayList<>(articleMap.values());
    }
    /**
     * @author xiaobenhai
     * @param offset
     * @param limit
     * @return List<Article>
     */
    public List<Article> listArticle(int offset,int limit){
        List<NodeMeta> nodeMetas = listNodeMeta(NodeType.ARTICLE,offset,limit,null);
        List<String> nodeIds = Lists.newArrayList();
        for(NodeMeta nodeMeta : nodeMetas){
            nodeIds.add(nodeMeta.getId());
        }
        Map<String,Article> articleMap = articleStore.findByNodeIds(nodeIds);
        return new ArrayList<>(articleMap.values());
    }
    /**
     * @author xiaobenhai
     * @return List<Article>
     */
    public List<Article> listArticle(){
        List<NodeMeta> nodeMetas = listNodeMeta(NodeType.ARTICLE,null);
        List<String> nodeIds = Lists.newArrayList();
        for(NodeMeta nodeMeta : nodeMetas){
            nodeIds.add(nodeMeta.getId());
        }
        Map<String,Article> articleMap = articleStore.findByNodeIds(nodeIds);
        return new ArrayList<>(articleMap.values());
    }

    public NodeMeta getNodeMeta(String nodeId) throws NoSuchNodeMetaException{
        NodeMeta nodeMeta = nodeMetaStore.findOne(nodeId);
        if(nodeMeta == null){
            throw new NoSuchNodeMetaException();
        }
        return nodeMeta;
    }

    /**
     * 通过父路径查找nodeMeta。
     * @param pfPath
     * @return
     * @throws NoSuchNodeMetaException
     */
    public NodeMeta getNodeMetaByPath(String pfPath) throws NoSuchNodeMetaException{
        NodeMeta nodeMeta = nodeMetaStore.findOneByPath(pfPath);
        if(nodeMeta == null){
            throw new NoSuchNodeMetaException();
        }
        return nodeMeta;
    }

    public List<NodeMeta> listNodeMeta(NodeType nodeType,int offset, int limit, String orderBy) {
        return nodeMetaStore.find(this.getPath(), nodeType, offset, limit, orderBy);
    }
    public List<NodeMeta> listNodeMeta(NodeType nodeType, String orderBy) {
        return nodeMetaStore.find(this.getPath(), nodeType, orderBy);
    }
    public List<NodeMeta> listNodeMetaAll(int offset ,int limit) {
        return nodeMetaStore.findAll(offset ,limit);
    }
}
