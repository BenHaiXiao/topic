package com.github.benhaixiao.topic.admin.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.github.benhaixiao.topic.exception.JacksonDeserializeException;
import com.github.benhaixiao.topic.follow.Follow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author xiaobenhai
 */
public class FollowJsonDeserializer {
    private static ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger LOG = LoggerFactory.getLogger(FollowJsonDeserializer.class);
    public static List<Follow> deserialize(String followJson) throws JacksonDeserializeException {
        try {
            List<Follow> follows= Lists.newArrayList();

            JsonNode rootNode = objectMapper.readTree(followJson);
            for(int i=0;i< rootNode.size();i++){
                JsonNode acclaimNode=rootNode.get(i);
                Follow follow = new Follow();
                follow.setUid(acclaimNode.get("uid").asText());
                follow.setTargetId(acclaimNode.get("tid").asText());
                follows.add(follow);
            }
            return follows;
        }catch (Exception e){
            LOG.warn("deserialize follow error. follow:{} msg:{}", followJson, e);
            throw new JacksonDeserializeException("deserialize followJson error.");
        }
    }
}
