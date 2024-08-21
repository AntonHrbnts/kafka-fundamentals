package kafka.fundamentals.a.hrabianets;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.admin.Admin;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class ConsumerMain {

    public static void main(String[] args) {

        printSysVariables();
        createTopic();

        KafkaConsumer consumer = getKafkaConsumer();
        consumer.subscribe(getTopics());

        System.out.println("Starting listener");
        while (true) {
            ConsumerRecords<String, String> recs = consumer.poll(10);
            if (recs.count() == 0) {
            } else {
                for (ConsumerRecord<String, String> rec : recs) {
                    System.out.println(String.format("Recieved %s: %s", rec.key(), rec.value()));
                }
            }
        }

    }

    private static void createTopic() {
        Admin admin = Admin.create(getBootstrapProperties());
        admin.createTopics(getNewTopics());
    }

    private static void printSysVariables() {
        Map<String, String> env = System.getenv();
        for (String envName : env.keySet()) {
            System.out.format("%s=%s%n", envName, env.get(envName));
        }
    }

    private static List<NewTopic> getNewTopics() {
        return Collections.singletonList(
                new NewTopic(System.getenv("TOPIC_NAME"),
                        Integer.parseInt(System.getenv("TOPIC_PARTITIONS")),
                        Short.parseShort(System.getenv("TOPIC_REPLICATIONS"))
                )
        );
    }

    private static List<String> getTopics() {
        return Collections.singletonList(System.getenv("TOPIC_NAME"));
    }

    private static KafkaConsumer getKafkaConsumer() {
        Properties props = getProperties();
        KafkaConsumer consumer = new KafkaConsumer(props);
        return consumer;
    }

    private static Properties getProperties() {
        Properties props = new Properties();
        props.putAll(getBootstrapProperties());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        System.out.println("GROUP_ID_CONFIG: " + System.getenv("GROUP_ID_CONFIG"));
        props.put(ConsumerConfig.GROUP_ID_CONFIG, System.getenv("GROUP_ID_CONFIG"));
        return props;
    }

    private static Properties getBootstrapProperties() {
        Properties props = new Properties();
        System.out.println("BOOTSTRAP_SERVERS_CONFIG: " + System.getenv("BOOTSTRAP_SERVERS_CONFIG"));
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, System.getenv("BOOTSTRAP_SERVERS_CONFIG"));
        return props;
    }
}