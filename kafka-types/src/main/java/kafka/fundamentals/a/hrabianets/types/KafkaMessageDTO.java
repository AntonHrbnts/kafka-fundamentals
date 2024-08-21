package kafka.fundamentals.a.hrabianets.types;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class KafkaMessageDTO {
    private final String host;
    private final String message;
}
