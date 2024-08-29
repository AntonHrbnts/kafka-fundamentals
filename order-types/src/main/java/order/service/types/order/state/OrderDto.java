package order.service.types.order.state;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import order.service.types.customer.CustomerDto;

import java.util.Date;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private Long id;
    private OrderState orderState;
    private Date createdAt;
    private Date updatedAt;
    private CustomerDto customer;
    private LocationDto location;
}
