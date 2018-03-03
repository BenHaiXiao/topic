package com.github.benhaixiao.topic.kafka;

import com.github.benhaixiao.topic.event.Event;
import com.github.benhaixiao.topic.shared.JsonMapper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * @author xiaobenhai
 *
 */
public class KProducer {

    private static Properties properties = new Properties();
    static {
//        properties.put("metadata.broker.list", "broker1:9092,broker2:9092 ");
//        properties.put("zk.connect","172.19.103.87:2181");
//        properties.put("serializer.class", "kafka.serializer.StringEncoder");
//        properties.put("partitioner.class", "example.producer.SimplePartitioner");
//        properties.put("request.required.acks", "1");
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"172.19.103.87:9091");
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());
    }

    private KafkaProducer<String,String> producer;

    private JsonMapper jsonMapper = JsonMapper.nonDefaultMapper();

    public void init(){
        producer = new KafkaProducer(properties);
    }

    private static final String TOPIC = "sloth-event";
    private static final String KEY = "sloth-key";
    public void send(Event event){
        String eventJson = jsonMapper.toJson(event);
        ProducerRecord<String,String> record = new ProducerRecord(TOPIC,eventJson);
        producer.send(record);
    }

    public void clean(){
        producer.close();
    }
}
