package com.github.benhaixiao.topic.admin.json;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.github.benhaixiao.topic.acclaim.Acclaim;
import com.github.benhaixiao.topic.exception.JacksonDeserializeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author xiaobenhai
 */
public class AcclaimJsonDeserializer {
    private static ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger LOG = LoggerFactory.getLogger(AcclaimJsonDeserializer.class);
    public static List<Acclaim>  deserialize(String acclaimJson) throws JacksonDeserializeException {
        try {
            List<Acclaim> acclaims=Lists.newArrayList();

            JsonNode rootNode = objectMapper.readTree(acclaimJson);
             for(int i=0;i< rootNode.size();i++){
                 JsonNode acclaimNode=rootNode.get(i);
                 Acclaim acclaim = new Acclaim();
                 acclaim.setUid(acclaimNode.get("uid").asText());
                 acclaim.setTargetId(acclaimNode.get("tid").asText());
                 acclaims.add(acclaim);
             }
            return acclaims;
        }catch (Exception e){
            LOG.warn("deserialize acclaim error. acclaim:{} msg:{}", acclaimJson, e);
            throw new JacksonDeserializeException("deserialize acclaimJson error.");
        }
    }
}
