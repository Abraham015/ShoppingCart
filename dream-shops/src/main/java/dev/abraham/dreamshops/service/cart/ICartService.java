package dev.abraham.dreamshops.service.cart;

import dev.abraham.dreamshops.model.Cart;

import java.math.BigDecimal;

public interface ICartService {
    Cart getCart(Long Id);
    void clearCart(Long Id);
    BigDecimal getTotalPrice(Long Id);
    Long initializeNewCart();
    Cart getCartByUserId(Long userId);
}
