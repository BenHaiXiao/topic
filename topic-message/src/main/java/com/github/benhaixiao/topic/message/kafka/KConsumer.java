package com.github.benhaixiao.topic.message.kafka;

import com.github.benhaixiao.topic.event.folder.FolderCreateEvent;
import com.github.benhaixiao.topic.event.Event;
import com.github.benhaixiao.topic.shared.JsonMapper;
import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;
import kafka.serializer.StringDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author xiaobenhai
 *
 */
public class KConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(KConsumer.class);

    private final ConsumerConnector consumer = Consumer.createJavaConsumerConnector(createConsumerConfig("172.19.103.87:2181","test"));
    private final String topic = "topic-event";

    private static ConsumerConfig createConsumerConfig(String a_zookeeper, String a_groupId) {
        Properties props = new Properties();
        props.put("zookeeper.connect", a_zookeeper);
        props.put("group.id", a_groupId);
        props.put("zookeeper.session.timeout.ms", "400");
        props.put("zookeeper.sync.time.ms", "200");
        props.put("auto.commit.interval.ms", "1000");

        return new ConsumerConfig(props);
    }


    private static final JsonMapper jsonMapper = JsonMapper.nonDefaultMapper();

    public void fetch(){
        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
        topicCountMap.put(topic,1);
        Map<String, List<KafkaStream<String, Event>>> consumerMap = consumer.createMessageStreams(topicCountMap, new StringDecoder(null),new EventDeconder());
        List<KafkaStream<String, Event>> streams = consumerMap.get(topic);

        for (final KafkaStream stream : streams) {
            ConsumerIterator<byte[], Event> it = stream.iterator();
            while (it.hasNext()){
                MessageAndMetadata<byte[],Event> messageAndMetadata = it.next();
                FolderCreateEvent folderCreateEvent = (FolderCreateEvent)messageAndMetadata.message();
                System.out.println("record:"+ folderCreateEvent.getFolder().getName());
            }
        }
    }

    public static void main(String[] args){
        KConsumer consumer = new KConsumer();
        consumer.fetch();
    }
}
