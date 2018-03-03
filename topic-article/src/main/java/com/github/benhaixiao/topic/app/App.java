package com.github.benhaixiao.topic.app;

import com.github.benhaixiao.topic.acclaim.AcclaimService;
import com.github.benhaixiao.topic.article.ArticleAcclaimStore;
import com.github.benhaixiao.topic.article.ArticleFollowStore;
import com.github.benhaixiao.topic.context.AppContext;
import com.github.benhaixiao.topic.folder.Folder;
import com.github.benhaixiao.topic.folder.FolderFinder;
import com.github.benhaixiao.topic.folder.FolderStore;
import com.github.benhaixiao.topic.follow.FollowService;
import com.github.benhaixiao.topic.node.NodeMetaStore;
import com.github.benhaixiao.topic.post.PostAcclaimStore;
import com.github.benhaixiao.topic.post.PostService;
import com.github.benhaixiao.topic.post.PostStore;
import com.github.benhaixiao.topic.shared.TokenType;
import com.github.benhaixiao.topic.user.UserStore;
import com.github.benhaixiao.topic.article.ArticleStore;
import com.github.benhaixiao.topic.domain.Dict;
import com.github.benhaixiao.topic.folder.FolderFollowStore;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

/**
 * @author xiaobenhai
 *
 */
@Document(collection = "app")
public class App {
    @Field("appid")
    private String appid;
    @Field("name")
    private String name;
    @Field("desc")
    private String description;
    @Field
    private TokenType tokenType;
    @Transient
    private AppStats appStats;
    @Transient
    private AppConfig appConfig;
    @Transient
    private FolderFinder folderFinder;
    @Transient
    private AppContext appContext;
    @Transient
    private UserStore userStore;
    @Transient
    private AcclaimService acclaimService;
    @Transient
    private FollowService followService;
    @Transient
    private PostService postService;
    @Transient
    private Dict dict = new Dict();

    public App(String appid,MongoTemplate slothMongoTemplate){
        this.appid = appid;
        //TODO
        this.appConfig = slothMongoTemplate.findOne(new Query(Criteria.where("appid").is(appid)),AppConfig.class);
        this.dict.initTagDict(appConfig.getTags());

        NodeMetaStore nodeMetaStore = new NodeMetaStore(slothMongoTemplate);
        ArticleStore articleStore = new ArticleStore(nodeMetaStore,slothMongoTemplate);
        FolderStore folderStore = new FolderStore(nodeMetaStore,slothMongoTemplate);
        FolderFollowStore folderFollowStore = new FolderFollowStore(slothMongoTemplate);
        ArticleAcclaimStore articleAcclaimStore = new ArticleAcclaimStore(slothMongoTemplate);
        PostStore postStore = new PostStore(slothMongoTemplate);
        PostAcclaimStore postAcclaimStore = new PostAcclaimStore(slothMongoTemplate);
       // AclStore aclStore=new AclStore();//author xiaobenhai
        ArticleFollowStore articleFollowStore =new ArticleFollowStore(slothMongoTemplate); //author xiaobenhai
        this.appContext = new AppContext(this,nodeMetaStore,folderStore, articleStore,postStore,postAcclaimStore, folderFollowStore, articleAcclaimStore,slothMongoTemplate,articleFollowStore);
        this.folderFinder = new FolderFinder(appContext);
        this.appContext.setFolderFinder(this.folderFinder);
        //TODO user store 初始化
        this.userStore = new UserStore(slothMongoTemplate);

        this.acclaimService = new AcclaimService(appContext);
        this.followService = new FollowService(appContext);
        this.postService = new PostService(appContext);

    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public AppStats getAppStats(){
        return appStats;
    }

    public void incTopicCount(){
        this.appContext.getSlothMongoTemplate().upsert(new Query(Criteria.where("appid").is(appid)),new Update().inc("tocCnt",1),AppStats.class);

    }

    public void decTopicCount(){
        this.appContext.getSlothMongoTemplate().updateFirst(new Query(Criteria.where("appid").is(appid)),new Update().inc("tocCnt",-1),AppStats.class);
    }

    public Folder getFolder(String path){
        return folderFinder.find(path);
    }

    public void incFolderCount(){
        this.appContext.getSlothMongoTemplate().upsert(new Query(Criteria.where("appid").is(appid)), new Update().inc("fdrCnt", 1), AppStats.class);
    }

    public void decFolderCount() {
        this.appContext.getSlothMongoTemplate().updateFirst(new Query(Criteria.where("appid").is(appid)),new Update().inc("fdrCnt",-1),AppStats.class);
    }
    public AppContext getAppContext() {
        return appContext;
    }

    public UserStore getUserStore() {
        return userStore;
    }

    public AcclaimService getAcclaimService() {
        return acclaimService;
    }

    public FollowService getFollowService() {
        return followService;
    }

    public PostService getPostService() {
        return postService;
    }

    public Dict getDict(){
        return dict;
    }
}
