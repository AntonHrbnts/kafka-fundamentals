package kafka.fundamentals.a.hrabianets.types;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.kafka.common.serialization.Serializer;

public class JsonSerializer implements Serializer {

    @SneakyThrows
    @Override
    public byte[] serialize(String topic, Object data) {
        return new ObjectMapper().writeValueAsBytes(data);
    }
}
