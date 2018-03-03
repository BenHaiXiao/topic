package com.github.benhaixiao.topic.domain;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;


/**
 * @author xiaobenhai
 *
 */
public class Dict {

    private BiMap<Integer,Tag> tagIdDict;
    private BiMap<String,Tag> tagNameDict;

    public Dict initTagDict(List<Tag> tags){
        tagIdDict = HashBiMap.create();
        tagNameDict = HashBiMap.create();
        if(CollectionUtils.isEmpty(tags)){
            return this;
        }
        for (Tag tag : tags){
            tagIdDict.put(tag.getId(), tag);
            tagNameDict.put(tag.getName(),tag);
        }
        return this;
    }

    public Tag findTag(Integer tagId){
        return tagIdDict.get(tagId);
    }

    public Tag findTag(String tagName){
        return tagNameDict.get(tagName);
    }
}
