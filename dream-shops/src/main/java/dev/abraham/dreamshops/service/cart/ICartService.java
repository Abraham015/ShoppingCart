package dev.abraham.dreamshops.service.cart;

import dev.abraham.dreamshops.model.Cart;
import dev.abraham.dreamshops.model.User;

import java.math.BigDecimal;

public interface ICartService {
    Cart getCart(Long Id);
    void clearCart(Long Id);
    BigDecimal getTotalPrice(Long Id);
    Cart initializeNewCart(User user);
    Cart getCartByUserId(Long userId);
}
