package dev.abraham.dreamshops.controller;

import dev.abraham.dreamshops.dto.OrderDTO;
import dev.abraham.dreamshops.exceptions.OrderNotFound;
import dev.abraham.dreamshops.model.Order;
import dev.abraham.dreamshops.response.APIResponse;
import dev.abraham.dreamshops.service.order.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {
    private final IOrderService orderService;


    @PostMapping("/add")
    public ResponseEntity<APIResponse> createOrder(@RequestParam Long userId){
        try {
            Order order=orderService.placeOrder(userId);
            OrderDTO orderDTO=orderService.castToOrderDTO(order);
            return ResponseEntity.ok(new APIResponse("Order placed successfully", orderDTO));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse("Error", e.getMessage()));
        }
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<APIResponse> getOrderById(@PathVariable Long orderId){
        try {
            OrderDTO order=orderService.getOrder(orderId);
            return ResponseEntity.ok(new APIResponse("Order obtained successfully", order));
        } catch (OrderNotFound e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<APIResponse> getUserOrders(@PathVariable Long userId){
        try {
            List<OrderDTO> order=orderService.getUserOrders(userId);
            return ResponseEntity.ok(new APIResponse("Orders obtained successfully", order));
        } catch (OrderNotFound e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }
}
