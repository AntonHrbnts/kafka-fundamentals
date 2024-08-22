package kafka.fundamentals.a.hrabianets;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.admin.Admin;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;

public class TopicInitMain {

    public static void main(String[] args) {
        printSysVariables();
        createTopic();
    }

    private static void createTopic() {
        try (Admin admin = Admin.create(getBootstrapProperties())) {
            admin.createTopics(getNewTopics());
        }
    }

    private static void printSysVariables() {
        Map<String, String> env = System.getenv();
        for (String envName : env.keySet()) {
            System.out.format("%s=%s%n", envName, env.get(envName));
        }
    }

    private static List<NewTopic> getNewTopics() {
        return Collections.singletonList(
                new NewTopic(System.getenv("TOPIC_NAME"), Integer.parseInt(System.getenv("TOPIC_PARTITIONS")),
                        Short.parseShort(System.getenv("TOPIC_REPLICATIONS"))));
    }

    private static Properties getBootstrapProperties() {
        Properties props = new Properties();
        System.out.println("BOOTSTRAP_SERVERS_CONFIG: " + System.getenv("BOOTSTRAP_SERVERS_CONFIG"));
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, System.getenv("BOOTSTRAP_SERVERS_CONFIG"));
        return props;
    }
}