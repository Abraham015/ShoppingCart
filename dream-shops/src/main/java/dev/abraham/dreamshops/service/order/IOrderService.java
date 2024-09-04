package dev.abraham.dreamshops.service.order;

import dev.abraham.dreamshops.dto.OrderDTO;
import dev.abraham.dreamshops.model.Order;

import java.util.List;

public interface IOrderService {
    Order placeOrder(Long userId);
    OrderDTO getOrder(Long orderId);
    List<OrderDTO> getUserOrders(Long userId);

    OrderDTO castToOrderDTO(Order order);
}
