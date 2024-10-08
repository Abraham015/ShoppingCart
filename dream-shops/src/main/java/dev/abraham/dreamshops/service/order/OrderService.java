package dev.abraham.dreamshops.service.order;

import dev.abraham.dreamshops.dto.OrderDTO;
import dev.abraham.dreamshops.enums.OrderStatus;
import dev.abraham.dreamshops.exceptions.OrderNotFound;
import dev.abraham.dreamshops.model.Cart;
import dev.abraham.dreamshops.model.Order;
import dev.abraham.dreamshops.model.OrderItem;
import dev.abraham.dreamshops.model.Product;
import dev.abraham.dreamshops.repository.OrderRepository;
import dev.abraham.dreamshops.repository.ProductRepository;
import dev.abraham.dreamshops.service.cart.ICartService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ICartService cartService;
    private final ModelMapper modelMapper;

    @Transactional
    public Order placeOrder(Long userId) {
        Cart cart=cartService.getCartByUserId(userId);
        Order order=createOrder(cart);
        order.setUser(cart.getUser());
        List<OrderItem> orderItemList=createOrderItems(order, cart);
        order.setOrderItems(new HashSet<>(orderItemList));
        order.setTotalAmount(calculateTotalAmount(orderItemList));
        Order savedOrder=orderRepository.save(order);
        cartService.clearCart(cart.getId());
        return savedOrder;
    }

    private Order createOrder(Cart cart) {
        Order order = new Order();
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());
        return order;
    }

    private List<OrderItem> createOrderItems(Order order, Cart cart) {
        return cart.getItems()
                .stream()
                .map(cartItem->{
                    Product product = cartItem.getProduct();
                    //Updating the inventory
                    product.setInventory(product.getInventory()-cartItem.getQuantity());
                    productRepository.save(product);
                    return new OrderItem(
                            order,
                            product,
                            cartItem.getQuantity(),
                            cartItem.getUnitPrice()
                    );
                }).toList();
    }

    private BigDecimal calculateTotalAmount(List<OrderItem> order) {
        return order.stream()
                .map(item->item.getPrice().multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public OrderDTO getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .map(this::castToOrderDTO)
                .orElseThrow(()->new OrderNotFound("Order not found"));
    }

    public List<OrderDTO> getUserOrders(Long userId) {
        return orderRepository.findByUserId(userId)
                .stream()
                .map(this::castToOrderDTO)
                .toList();
    }

    public OrderDTO castToOrderDTO(Order order) {
        return modelMapper.map(order, OrderDTO.class);
    }
}
