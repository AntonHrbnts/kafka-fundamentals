package kafka.fundamentals.a.hrabianets;

import java.util.Map;
import java.util.Properties;

import kafka.fundamentals.a.hrabianets.types.JsonSerializer;
import kafka.fundamentals.a.hrabianets.types.KafkaMessageDTO;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

public class ProducerMain {

    private static final String HOSTNAME = System.getenv("HOSTNAME");

    public static void main(String[] args) throws InterruptedException {

        Map<String, String> env = System.getenv();
        for (String envName : env.keySet()) {
            System.out.format("%s=%s%n", envName, env.get(envName));
        }

        try (KafkaProducer<String, KafkaMessageDTO> producer = getStringStringKafkaProducer()) {
            for (int i = 0; i < 1000; i++) {
                ProducerRecord<String, KafkaMessageDTO> data;

                if (i % 2 == 0) {
                    data = new ProducerRecord<>("even", Integer.toString(i),
                            KafkaMessageDTO.builder().host(HOSTNAME).message(String.format("%d is even", i)).build());
                } else {
                    KafkaMessageDTO messageDTO = KafkaMessageDTO.builder().host(HOSTNAME).message(String.format("%d is odd", i)).build();
                    data = new ProducerRecord<>("odd", Integer.toString(i), messageDTO);
                }
                producer.send(data);
                Thread.sleep(1000L);
            }
        }
    }

    private static KafkaProducer<String, KafkaMessageDTO> getStringStringKafkaProducer() {
        return new KafkaProducer<>(getProperties());
    }

    private static Properties getProperties() {
        Properties props = new Properties();
        System.out.println("BOOTSTRAP_SERVERS_CONFIG: " + System.getenv("BOOTSTRAP_SERVERS_CONFIG"));
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, System.getenv("BOOTSTRAP_SERVERS_CONFIG"));

        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());
        System.out.println("GROUP_ID_CONFIG: " + System.getenv("GROUP_ID_CONFIG"));
        props.put(ConsumerConfig.GROUP_ID_CONFIG, System.getenv("GROUP_ID_CONFIG"));
        return props;
    }
}
