package dev.abraham.dreamshops.service.order;

import dev.abraham.dreamshops.model.Order;

public interface IOrderService {
    Order placeOrder(Long userId);
    Order getOrder(Long orderId);
}
