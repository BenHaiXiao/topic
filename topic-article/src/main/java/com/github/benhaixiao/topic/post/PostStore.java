package com.github.benhaixiao.topic.post;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;
import java.util.Set;

/**
 * @author xiaobenhai
 *
 */
public class PostStore {

    private MongoTemplate postMongoTemplate;

    public PostStore(MongoTemplate postMongoTemplate) {
        this.postMongoTemplate = postMongoTemplate;
    }

    public Post store(Post post){
        post.setValid(1);
        postMongoTemplate.save(post);
        PostIndex postIndex = new PostIndex();
        if(post.getReplyId() == null || post.getReplyId() == ""){
            postIndex.setId(post.getId());
            postIndex.setTopId(post.getId());
            List<String> ids = Lists.newArrayList();
            ids.add(post.getId());
            postIndex.setIds(ids);
            postMongoTemplate.save(postIndex);
        }else{
            PostIndex parentPostIndex = postMongoTemplate.findOne(new Query(Criteria.where("_id").is(post.getReplyId())),PostIndex.class);
            postMongoTemplate.updateFirst(new Query(Criteria.where("_id").is(parentPostIndex.getTopId())), new Update().push("ids", post.getId()), PostIndex.class);
            PostIndex newPostIndex = new PostIndex();
            newPostIndex.setId(post.getId());
            newPostIndex.setTopId(postIndex.getTopId());
            postMongoTemplate.save(newPostIndex);
        }
        return post;
    }

    public int remove(String postId){
        WriteResult writeResult = postMongoTemplate.updateFirst(new Query(Criteria.where("_id").is(postId)), new Update().set("valid", 0), Post.class);
        if(writeResult.getN() == 1){
            PostIndex postIndex = postMongoTemplate.findOne(new Query(Criteria.where("_id").is(postId)), PostIndex.class);
            if(!postIndex.getId().equals(postIndex.getTopId())){
                WriteResult uWResult = postMongoTemplate.updateFirst(new Query(Criteria.where("_id").is(postIndex.getTopId())),new Update().pull("ids",postId),PostIndex.class);
                if(uWResult.getN() != 1){
                    return 0;
                }
            }
            postMongoTemplate.remove(new Query(Criteria.where("_id").is(postId)), PostIndex.class);
        }
        return 1;
    }

    public List<Post> find(String targetId,int offset, int limit,String orderBy){
       List<Post> posts = postMongoTemplate.find(new Query(Criteria.where("tid").is(targetId).and("valid").is(1)).skip(offset).limit(limit), Post.class);
           if(posts == null){
            return Lists.newArrayList();
        }
        return posts;
    }
    public List<Post> find(String targetId,String orderBy){
        List<Post> posts = postMongoTemplate.find(new Query(Criteria.where("tid").is(targetId).and("valid").is(1)), Post.class);
        if(posts == null){
            return Lists.newArrayList();
        }
        return posts;
    }

    public List<Post> findAll(int offset,int limit){
        List<Post> posts = postMongoTemplate.find(new Query(Criteria.where("valid").is(1)).skip(offset).limit(limit),Post.class);
        if(posts == null){
            return Lists.newArrayList();
        }
        return posts;
    }
    public List<Post> findAll(){
        List<Post> posts = postMongoTemplate.find(new Query(Criteria.where("valid").is(1)),Post.class);
        if(posts == null){
            return Lists.newArrayList();
        }
        return posts;
    }
    public Post findOne(String PostId){
        Post post=postMongoTemplate.findOne(new Query(Criteria.where("valid").is(1).and("id").is(PostId)), Post.class);
        return  post;
    }

    public List<Post> findAncestors(List<Post> posts){
        List<String> ids = Lists.newArrayList();
        for(Post post : posts){
            ids.add(post.getId());
        }

        List<PostIndex> postIndexes = postMongoTemplate.find(new Query(Criteria.where("_id").in(ids)),PostIndex.class);
        Set<String> topIds = Sets.newHashSet();
        for(PostIndex postIndex : postIndexes){
            if(postIndex.getId().equals(postIndex.getTopId())){
                continue;
            }
            topIds.add(postIndex.getTopId());
        }
        List<PostIndex> topPostIndexes = postMongoTemplate.find(new Query(Criteria.where("_id").in(topIds)),PostIndex.class);
        Set<String> ancestorIds = Sets.newHashSet();
        for(PostIndex postIndex : postIndexes){
            ancestorIds.addAll(postIndex.getIds());
        }
        List<Post> ancestorPosts = postMongoTemplate.find(new Query(Criteria.where("_id").in(ancestorIds)),Post.class);
        return ancestorPosts;
    }


    public long incAcclaimCount(String postId){
        DBCollection nodeMetaColl = postMongoTemplate.getCollection("post");
        BasicDBObject ref = new BasicDBObject();
        ref.put("_id",new ObjectId(postId));
        BasicDBObject update = new BasicDBObject();
        update.put("$inc",new BasicDBObject().append("acmCnt",1L));
        DBObject result = nodeMetaColl.findAndModify(ref, new BasicDBObject("acmCnt",1), null, false, update, true, false);
        return result == null ? 0 : ((BasicDBObject)result).getLong("acmCnt",0);
    }

    public long decAcclaimCount(String postId){
        DBCollection nodeMetaColl = postMongoTemplate.getCollection("post");
        BasicDBObject ref = new BasicDBObject();
        ref.put("_id",new ObjectId(postId));
        BasicDBObject update = new BasicDBObject();
        update.put("$inc",new BasicDBObject().append("acmCnt",-1L));
        DBObject result = nodeMetaColl.findAndModify(ref, new BasicDBObject("acmCnt",1), null, false, update, true, false);
        return result == null ? 0 : ((BasicDBObject)result).getLong("acmCnt",0);    }
}
