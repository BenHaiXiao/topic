package com.github.benhaixiao.topic.shared;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benhaixiao.topic.event.Event;
import com.github.benhaixiao.topic.event.NullEvent;
import kafka.serializer.Decoder;
import kafka.serializer.StringDecoder;

/**
 * @author xiaobenhai
 *
 */
public class EventDeconder implements Decoder<Event> {

    private static final JsonMapper jsonMapper = JsonMapper.nonEmptyMapper();
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private StringDecoder stringDecoder = new StringDecoder(null);
    @Override
    public Event fromBytes(byte[] bytes) {
        try {
            String eventJson = stringDecoder.fromBytes(bytes);
            JsonNode eventJsonNode = objectMapper.readTree(eventJson);
            String jsonType = eventJsonNode.get("type").asText();
            Event.Type type = Event.Type.valueOf(jsonType);
            Event ret = (Event) jsonMapper.fromJson(eventJson, type.getEventClazz());
            return ret;
        }catch (Exception e){
            return NullEvent.getInstance();
        }
    }
}
