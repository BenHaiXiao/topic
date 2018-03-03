package com.github.benhaixiao.topic.shared;

import com.github.benhaixiao.topic.event.Event;
import kafka.serializer.Encoder;
import kafka.serializer.StringEncoder;

/**
 * @author xiaobenhai
 *
 */
public class EventEncoder implements Encoder<Event> {
    private static final JsonMapper jsonMapper = JsonMapper.nonDefaultMapper();
    private static final StringEncoder stringEncoder = new StringEncoder(null);
    @Override
    public byte[] toBytes(Event event) {
        return stringEncoder.toBytes(jsonMapper.toJson(event));
    }
}
