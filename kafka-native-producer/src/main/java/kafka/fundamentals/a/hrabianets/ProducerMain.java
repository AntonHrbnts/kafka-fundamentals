package kafka.fundamentals.a.hrabianets;

import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

public class ProducerMain {
    public static void main(String[] args) throws InterruptedException {
        try (KafkaProducer<String, String> producer = getStringStringKafkaProducer()) {
            for (int i = 0; i < 1000; i++) {
                ProducerRecord<String, String> data;

                if (i % 2 == 0) {
                    data = new ProducerRecord<String, String>("even",  Integer.toString(i),
                            String.format("%d is even", i));
                } else {
                    data = new ProducerRecord<String, String>("odd",  Integer.toString(i),
                            String.format("%d is odd", i));
                }
                producer.send(data);
                Thread.sleep(1000L);
            }
        }
    }

    private static KafkaProducer<String, String> getStringStringKafkaProducer() {
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(getProperties());
        return producer;
    }

    private static Properties getProperties() {
        Properties props = new Properties();
        System.out.println("BOOTSTRAP_SERVERS_CONFIG: " + System.getenv("BOOTSTRAP_SERVERS_CONFIG"));
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, System.getenv("BOOTSTRAP_SERVERS_CONFIG"));

        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringSerializer");
        System.out.println("GROUP_ID_CONFIG: " + System.getenv("GROUP_ID_CONFIG"));
        props.put(ConsumerConfig.GROUP_ID_CONFIG, System.getenv("GROUP_ID_CONFIG"));
        return props;
    }
}
