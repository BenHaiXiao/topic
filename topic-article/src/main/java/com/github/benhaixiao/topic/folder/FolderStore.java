package com.github.benhaixiao.topic.folder;

import com.google.common.collect.Maps;
import com.github.benhaixiao.topic.node.NodeMetaStore;
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
public class FolderStore {

    private MongoTemplate slothMongoTemplate;

    private NodeMetaStore nodeMetaStore;

    public FolderStore(NodeMetaStore nodeMetaStore,MongoTemplate slothMongoTemplate){
        this.nodeMetaStore = nodeMetaStore;
        this.slothMongoTemplate = slothMongoTemplate;
    }

    public Folder save(Folder folder){
        folder.setValid(1);
        slothMongoTemplate.save(folder);
        return folder;
    }

    public void remove(Folder folder){
        //TODO 事务
        folder.setValid(0);
        this.slothMongoTemplate.updateFirst(new Query(Criteria.where("_id").is(folder.getNodeId())), new Update().set("valid", 0), Folder.class);
        //删除对应NodeMeta节点
        nodeMetaStore.remove(nodeMetaStore.findOne(folder.getNodeId()));
    }

    public Folder findOne(String path){
        Folder folder = slothMongoTemplate.findOne(new Query(Criteria.where("path").is(path).and("valid").is(1)),Folder.class);
        return folder;
    }

    public Map<String,Folder> findIn(List<String> paths){
        List<Folder> folders = slothMongoTemplate.find(new Query(Criteria.where("path").in(paths).and("valid").is(1)),Folder.class);
        Map<String,Folder> result = Maps.newHashMap();
        for(Folder folder : folders){
            result.put(folder.getPath(),folder);
        }
        return result;
    }
    public Map<String,Folder> findFolderAll(){
        List<Folder> folders = slothMongoTemplate.find(new Query(Criteria.where("valid").is(1)),Folder.class);
        Map<String,Folder> result = Maps.newHashMap();
        for(Folder folder : folders){
            result.put(folder.getPath(),folder);
        }
        return result;
    }
    public Map<String,Folder> findFolderAll( int offset ,int limit){
        List<Folder> folders = slothMongoTemplate.find(new Query(Criteria.where("valid").is(1)).skip(offset).limit(limit),Folder.class);
        Map<String,Folder> result = Maps.newHashMap();
        for(Folder folder : folders){
            result.put(folder.getPath(),folder);
        }
        return result;
    }

    public Map<String,Folder> findByNodeIds(List<String> nodeIds){
        List<Folder> folders = slothMongoTemplate.find(new Query(Criteria.where("_id").in(nodeIds).and("valid").is(1)),Folder.class);
        Map<String,Folder> result = Maps.newHashMap();
        for(Folder folder : folders){
            result.put(folder.getNodeId(),folder);
        }
        return result;
    }

}
