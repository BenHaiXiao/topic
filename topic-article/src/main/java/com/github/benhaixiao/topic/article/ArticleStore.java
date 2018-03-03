package com.github.benhaixiao.topic.article;

import com.github.benhaixiao.topic.node.NodeMetaStore;
import com.google.common.collect.Maps;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;
import java.util.Map;

/**
 * @author xiaobenhai
 *
 */
public class ArticleStore {

    private MongoTemplate articleMongoTemplate;

    private NodeMetaStore nodeMetaStore;

    public ArticleStore(NodeMetaStore nodeMetaStore,MongoTemplate articleMongoTemplate){
        this.nodeMetaStore = nodeMetaStore;
        this.articleMongoTemplate = articleMongoTemplate;
    }

    public Article store(Article article){
        article.setValid(1);
        this.articleMongoTemplate.save(article);
        return article;
    }
    public void remove(Article article){
        article.setValid(0);
        this.articleMongoTemplate.updateFirst(new Query(Criteria.where("_id").is(article.getNodeId())), new Update().set("valid", 0), Article.class);
        //删除对应NodeMeta节点
        nodeMetaStore.remove(nodeMetaStore.findOne(article.getNodeId()));
    }
    public Article findOne(String topicId){
        return articleMongoTemplate.findOne(new Query(Criteria.where("_id").is(topicId).and("valid").is(1)), Article.class);
    }
    public  Map<String ,Article> findArticleAll(){
        List<Article> articles=articleMongoTemplate.find(new Query(Criteria.where("valid").is(1)),Article.class);
        Map<String,Article> result=Maps.newHashMap();
        for (Article article :articles)
        {
            result.put(article.getNodeId(),article);
        }
        return  result;
    }
    public  Map<String ,Article> findArticleAll(int offset,int limit){
        List<Article> articles=articleMongoTemplate.find(new Query(Criteria.where("valid").is(1)).skip(offset).limit(limit),Article.class);
        Map<String,Article> result=Maps.newHashMap();
        for (Article article :articles)
        {
            result.put(article.getNodeId(),article);
        }
        return  result;
    }

    public Map<String, Article> findByNodeIds(List<String> nodeIds) {
        List<Article> articles = articleMongoTemplate.find(new Query(Criteria.where("_id").in(nodeIds).and("valid").is(1)), Article.class);
        Map<String,Article> result = Maps.newHashMap();
        for(Article article : articles){
            result.put(article.getNodeId(), article);
        }
        return result;
    }
}
