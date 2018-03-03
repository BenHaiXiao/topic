package com.github.benhaixiao.topic.context;

import com.github.benhaixiao.topic.folder.FolderStore;
import com.github.benhaixiao.topic.article.ArticleAcclaimStore;
import com.github.benhaixiao.topic.article.ArticleFollowStore;
import com.github.benhaixiao.topic.app.App;
import com.github.benhaixiao.topic.article.ArticleStore;
import com.github.benhaixiao.topic.folder.FolderFinder;
import com.github.benhaixiao.topic.folder.FolderFollowStore;
import com.github.benhaixiao.topic.node.NodeMetaStore;
import com.github.benhaixiao.topic.post.PostAcclaimStore;
import com.github.benhaixiao.topic.post.PostStore;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * @author xiaobenhai
 *
 */
public class AppContext {
    private MongoTemplate slothMongoTemplate;
    private App app;
    private ArticleStore articleStore;
    private NodeMetaStore nodeMetaStore;
    private FolderStore folderStore;
    private ArticleAcclaimStore articleAcclaimStore;
    private PostStore postStore;
    private FolderFollowStore folderFollowStore;
    private PostAcclaimStore postAcclaimStore;
    private FolderFinder folderFinder;
    private ArticleFollowStore articleFollowStore;


    public AppContext(MongoTemplate slothMongoTemplate, App app, ArticleStore articleStore, NodeMetaStore nodeMetaStore, FolderStore folderStore, ArticleAcclaimStore articleAcclaimStore, PostStore postStore, FolderFollowStore folderFollowStore, PostAcclaimStore postAcclaimStore, FolderFinder folderFinder, ArticleFollowStore articleFollowStore) {
        this.slothMongoTemplate = slothMongoTemplate;
        this.app = app;
        this.articleStore = articleStore;
        this.nodeMetaStore = nodeMetaStore;
        this.folderStore = folderStore;
        this.articleAcclaimStore = articleAcclaimStore;
        this.postStore = postStore;
        this.folderFollowStore = folderFollowStore;
        this.postAcclaimStore = postAcclaimStore;
        this.folderFinder = folderFinder;
        this.articleFollowStore = articleFollowStore;

    }

    public AppContext(App app, NodeMetaStore nodeMetaStore,FolderStore folderStore, ArticleStore articleStore,PostStore postStore,PostAcclaimStore postAcclaimStore,FolderFollowStore folderFollowStore, ArticleAcclaimStore articleAcclaimStore,MongoTemplate slothMongoTemplate,ArticleFollowStore articleFollowStore) {
        this.nodeMetaStore = nodeMetaStore;
        this.slothMongoTemplate = slothMongoTemplate;
        this.app = app;
        this.articleStore = articleStore;
        this.folderStore = folderStore;
        this.postStore = postStore;
        this.postAcclaimStore = postAcclaimStore;
        this.folderFollowStore = folderFollowStore;
        this.articleAcclaimStore = articleAcclaimStore;
        this.articleFollowStore=articleFollowStore;

    }


    public MongoTemplate getSlothMongoTemplate() {
        return slothMongoTemplate;
    }
    public App getApp() {
        return app;
    }

    public ArticleStore getArticleStore() {
        return articleStore;
    }

    public NodeMetaStore getNodeMetaStore() {
        return nodeMetaStore;
    }

    public FolderStore getFolderStore(){
        return folderStore;
    }

    public ArticleAcclaimStore getArticleAcclaimStore() {
        return articleAcclaimStore;
    }

    public PostAcclaimStore getPostAcclaimStore() {
        return postAcclaimStore;
    }

    public PostStore getPostStore() {
        return postStore;
    }

    public FolderFollowStore getFolderFollowStore() {
        return folderFollowStore;
    }

    public ArticleFollowStore getArticleFollowStore() {
        return articleFollowStore;
    }

    public FolderFinder getFolderFinder() {
        return folderFinder;
    }

    public void setFolderFinder(FolderFinder folderFinder) {
        this.folderFinder = folderFinder;
    }
}
