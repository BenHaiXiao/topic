package com.github.benhaixiao.topic.folder;

import com.mongodb.WriteResult;
import com.github.benhaixiao.topic.acclaim.Acclaim;
import com.github.benhaixiao.topic.exception.DuplicateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

/**
 * @author xiaobenhai
 *
 */
public class FolderAcclaimStore {

    private static final Logger LOG = LoggerFactory.getLogger(FolderAcclaimStore.class);

    private MongoTemplate acclaimMongoTemplate;

    private static final String COLLECTION = "folder_acclaim";

    public FolderAcclaimStore(MongoTemplate acclaimMongoTemplate) {
        this.acclaimMongoTemplate = acclaimMongoTemplate;
    }

    public Acclaim store(Acclaim acclaim) throws DuplicateException{
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

    public Acclaim findOne(String uid, String targetId) {
        Acclaim acclaim = acclaimMongoTemplate.findOne(new Query(Criteria.where("uid").is(uid).and("tid").is(targetId)),Acclaim.class,COLLECTION);
        return acclaim;
    }
}
