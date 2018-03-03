package com.github.benhaixiao.topic.node;

import com.google.common.collect.Maps;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import org.apache.commons.collections.CollectionUtils;
import org.bson.types.ObjectId;
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
public class NodeMetaStore {

    private MongoTemplate nodeModeMongoTemplate;

    public NodeMetaStore(MongoTemplate nodeModeMongoTemplate){
        this.nodeModeMongoTemplate = nodeModeMongoTemplate;
    }

    public NodeMeta save(NodeMeta nodeMeta){
        nodeMeta.setValid(1);
        nodeModeMongoTemplate.save(nodeMeta);
        return nodeMeta;
    }

    public void remove(NodeMeta nodeMeta) {
        nodeMeta.setValid(0);
        this.nodeModeMongoTemplate.updateFirst(new Query(Criteria.where("_id").is(nodeMeta.getId())), new Update().set("valid", 0), NodeMeta.class);
    }

    public NodeMeta findOne(String nodeId) {
        return nodeModeMongoTemplate.findOne(new Query(Criteria.where("_id").is(nodeId).and("valid").is(1)),NodeMeta.class);
    }

    /**
     * xiaobenhai
     * 根据父路径查找nodeMeta
     * @param pfPath
     * @return
     */
    public NodeMeta findOneByPath(String pfPath) {
        return nodeModeMongoTemplate.findOne(new Query(Criteria.where("pfpath").is(pfPath).and("valid").is(1)),NodeMeta.class);
    }
    public List<NodeMeta> find(String path, NodeType nodeType, String orderBy) {
        return nodeModeMongoTemplate.find(new Query(Criteria.where("pfpath").is(path).and("type").is(nodeType).and("valid").is(1)), NodeMeta.class);
    }
    public List<NodeMeta> findAll(int offset,int limit) {
        return nodeModeMongoTemplate.find(new Query().skip(offset).limit(limit), NodeMeta.class);
    }

    public List<NodeMeta> find(String path, NodeType nodeType, int offset, int limit, String orderBy) {
        return nodeModeMongoTemplate.find(new Query(Criteria.where("pfpath").is(path).and("type").is(nodeType).and("valid").is(1)).skip(offset).limit(limit),NodeMeta.class);
    }

    public Map<String,NodeMeta> findIn(List<String> ids){
        List<NodeMeta> nodeMetaes = nodeModeMongoTemplate.find(new Query(Criteria.where("_id").in(ids).and("valid").is(1)),NodeMeta.class);
        Map<String,NodeMeta> result = Maps.newHashMap();
        if(CollectionUtils.isEmpty(nodeMetaes)){
            return result;
        }
        for(NodeMeta nodeMeta : nodeMetaes){
            result.put(nodeMeta.getId(),nodeMeta);
        }
        return result;
    }

    public long incStatsCount(String nodeId,String field){
        DBCollection nodeMetaColl = nodeModeMongoTemplate.getCollection("nodeMeta");
        BasicDBObject ref = new BasicDBObject();
        ref.put("_id",new ObjectId(nodeId));
        BasicDBObject update = new BasicDBObject();
        update.put("$inc",new BasicDBObject().append("stats."+field,1L));
        DBObject result = nodeMetaColl.findAndModify(ref, new BasicDBObject("stats."+field,1), null, false, update, true, true);
        BasicDBObject stats = (BasicDBObject)result.get("stats");
        return stats == null ? 0 : stats.getLong(field,0);
    }

    public long decStatsCount(String nodeId,String field){
        DBCollection nodeMetaColl = nodeModeMongoTemplate.getCollection("nodeMeta");
        BasicDBObject ref = new BasicDBObject();
        ref.put("_id",new ObjectId(nodeId));
        BasicDBObject update = new BasicDBObject();
        update.put("$inc",new BasicDBObject().append("stats."+field,-1L));
        DBObject result = nodeMetaColl.findAndModify(ref, new BasicDBObject("stats."+field,1), null, false, update, true, false);
        BasicDBObject stats = (BasicDBObject)result.get("stats");
        return stats == null ? 0 : stats.getLong(field,0);
    }

}
