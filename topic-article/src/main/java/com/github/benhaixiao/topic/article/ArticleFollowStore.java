package com.github.benhaixiao.topic.article;

import com.github.benhaixiao.topic.follow.Follow;
import com.mongodb.WriteResult;
import com.github.benhaixiao.topic.exception.DuplicateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import java.util.List;

/**
 * @author xiaobenhai
 *
 */
public class ArticleFollowStore {

    private static final Logger LOG = LoggerFactory.getLogger(ArticleFollowStore.class);

    private MongoTemplate followMongoTemplate;

    private static final String COLLECTION = "article_follow";

    public ArticleFollowStore(MongoTemplate followMongoTemplate) {
        this.followMongoTemplate = followMongoTemplate;
    }

    public Follow store(Follow follow) throws DuplicateException{
        try {
            followMongoTemplate.save(follow,COLLECTION);
        }catch (DuplicateKeyException e){
            throw new DuplicateException("duplicate follow.");
        }
        return follow;
    }

    public int remove(Follow follow){
        WriteResult writeResult = followMongoTemplate.remove(new Query(Criteria.where("uid").is(follow.getUid()).and("tid").is(follow.getTargetId())),COLLECTION);
        return writeResult.getN();
    }

    public Follow findOne(String uid,String targetId){
        Follow follow = followMongoTemplate.findOne(new Query(Criteria.where("uid").is(uid).and("tid").is(targetId)), Follow.class, COLLECTION);
        return follow;
    }
    /**
     *获取关注对象的起始offer，limit条关注
     * @author xiaobenhai
     * @param
     * @return Long
     */
    public List<Follow> listByTargetId(String targerId,Integer offer,Integer limit){
        List<Follow> follows =followMongoTemplate.find(new Query(Criteria.where("tid").is(targerId)).skip(offer).limit(limit), Follow.class, COLLECTION);
        return  follows;
    }
    /**
     *获取关注对象的关注数量
     * @author QuPeng
     * @param targerId
     * @return
     */
    public Long getTargetIdCount(String targerId) {
        Long num = followMongoTemplate.count(new Query(Criteria.where("tid").is(targerId)), Follow.class, COLLECTION);
        return  num;
    }

    /**
     *获取起始offer，limit条关注
     * @author xiaobenhai
     * @param
     * @return List<Follow>
     */
    public List<Follow> listAll(Integer offer,Integer limit){
        List<Follow> follows =followMongoTemplate.find(new Query().skip(offer).limit(limit), Follow.class, COLLECTION);
        return  follows;
    }
    /**
     *获取所有关注数量
     * @author xiaobenhai
     * @param
     * @return Long
     */
    public Long getTotalCount(){
        Long count=followMongoTemplate.count(new Query(), Follow.class, COLLECTION);
        return  count;
    }

    /**
     *��ȡ�����µ�ȫ����ע
     * @author QuPeng
     * @param targetId
     * @return List<Follow>
     */
    public List<Follow> find(String targetId) {
        return followMongoTemplate.find(new Query(Criteria.where("tid").is(targetId)), Follow.class, COLLECTION);
    }
}
