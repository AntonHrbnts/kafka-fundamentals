package order.service.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import order.service.data.OrderEntity;
import order.service.data.OrderRepository;

@Component
@RequiredArgsConstructor
public class OrderListener {

    private final OrderRepository orderRepository;

    @KafkaListener(topics = "order", groupId = "order-service")
    public void listen(@Payload String orderMessage) {
        OrderEntity order = new OrderEntity();
        order.setMessage(orderMessage);
        orderRepository.save(order);
    }
}
