package com.github.benhaixiao.topic.post;

import com.mongodb.WriteResult;
import com.github.benhaixiao.topic.acclaim.Acclaim;
import com.github.benhaixiao.topic.exception.DuplicateException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

/**
 * @author xiaobenhai
 *
 */
public class PostAcclaimStore {

    private MongoTemplate acclaimMongoTemplate;

    private String COLLECTION = "post_acclaim";

    public PostAcclaimStore(MongoTemplate acclaimMongoTemplate) {
        this.acclaimMongoTemplate = acclaimMongoTemplate;
    }

    public Acclaim store(Acclaim acclaim) throws DuplicateException {
        try {
            acclaimMongoTemplate.save(acclaim,COLLECTION);
        }catch (Exception e){
            throw new DuplicateException("duplicate acclaim.");
        }
        return acclaim;
    }

    public int remove(Acclaim acclaim){
        WriteResult writeResult = acclaimMongoTemplate.remove(new Query(Criteria.where("uid").is(acclaim.getUid()).and("tid").is(acclaim.getTargetId())),COLLECTION);
        return writeResult.getN();
    }
    public int remove(String acclaimId){
        WriteResult writeResult = acclaimMongoTemplate.remove(new Query(Criteria.where("_id").is(acclaimId)),COLLECTION);
        return writeResult.getN();
    }

    public Acclaim findOne(String uid, String targetId) {
        Acclaim acclaim = acclaimMongoTemplate.findOne(new Query(Criteria.where("uid").is(uid).and("tid").is(targetId)), Acclaim.class, COLLECTION);
        return acclaim;
    }
    /**
     *获取点赞对象的起始offer，limit条点赞
     * @author xiaobenhai
     * @param
     * @return Long
     */
    public List<Acclaim> listByTargetId(String targerId,Integer offer,Integer limit){
        List<Acclaim> acclaims =acclaimMongoTemplate.find(new Query(Criteria.where("tid").is(targerId)).skip(offer).limit(limit), Acclaim.class, COLLECTION);
        return  acclaims;
    }
    /**
     *获取点赞对象的点赞数量
     * @author QuPeng
     * @param targerId
     * @return
     */
    public Long getTargetIdCount(String targerId) {
        Long num = acclaimMongoTemplate.count(new Query(Criteria.where("tid").is(targerId)), Acclaim.class, COLLECTION);
        return  num;
    }

    /**
     *获取起始offer，limit条点赞
     * @author xiaobenhai
     * @param
     * @return List<Acclaim>
     */
    public List<Acclaim> listAll(Integer offer,Integer limit){
        List<Acclaim> acclaims =acclaimMongoTemplate.find(new Query().skip(offer).limit(limit), Acclaim.class, COLLECTION);
        return  acclaims;
    }
    /**
     *获取所有点赞数量
     * @author xiaobenhai
     * @param
     * @return Long
     */
    public Long getTotalCount(){
        Long count=acclaimMongoTemplate.count(new Query(), Acclaim.class, COLLECTION);
        return  count;
    }
}
