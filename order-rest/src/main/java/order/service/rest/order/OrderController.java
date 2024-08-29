package order.service.rest.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import order.service.types.order.state.OrderDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController()
public class OrderController {

    private final OrderSender orderSender;

    @PostMapping(path = "/orders", consumes = "application/json")
    public void create(@RequestBody OrderDto order) throws JsonProcessingException {
        orderSender.send(order);
    }
}
