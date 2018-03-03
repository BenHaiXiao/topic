package com.github.benhaixiao.topic.shared;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benhaixiao.topic.event.Event;
import com.github.benhaixiao.topic.exception.JacksonDeserializeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xiaobenhai
 *
 */
public class EventJacksonDeserializer {

    private static final Logger LOG = LoggerFactory.getLogger(EventJacksonDeserializer.class);


    private static JsonMapper jsonMapper = JsonMapper.nonDefaultMapper();
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static Event deserialize(String eventJson){
        try {
            JsonNode eventJsonNode = objectMapper.readTree(eventJson);
            String jsonType = eventJsonNode.get("type").asText();
            Event.Type type = Event.Type.valueOf(jsonType);
            Event ret = (Event)jsonMapper.fromJson(eventJson,type.getEventClazz());
            return ret;

        }catch (Exception e){
            LOG.warn("deserialize event error. article:{} msg:{}", eventJson, e);
            throw new JacksonDeserializeException("deserialize event error.");
        }
    }
}
