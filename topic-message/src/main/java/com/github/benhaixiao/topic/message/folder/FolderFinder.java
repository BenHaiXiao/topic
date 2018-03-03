package com.github.benhaixiao.topic.message.folder;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.github.benhaixiao.topic.message.store.MessageStore;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;
import java.util.Map;

/**
 * @author xiaobenhai
 *
 */
public class FolderFinder {
    private Map<String,Folder> folderMap = Maps.newHashMap();
    public FolderFinder(MongoTemplate msgMongoTemplate){
        folderMap.put("notice",new Folder("notice",new MessageStore(msgMongoTemplate,"notice_message")));
        folderMap.put("reply",new Folder("reply",new MessageStore(msgMongoTemplate,"reply_message")));
    }

    public Folder findOne(String path){
        return folderMap.get(path);
    }
    public List<Folder> findAll(){
        return Lists.newArrayList(folderMap.values());
    }
}
