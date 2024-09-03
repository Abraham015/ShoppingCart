package dev.abraham.dreamshops.service.cart;

import dev.abraham.dreamshops.exceptions.CartNotFound;
import dev.abraham.dreamshops.model.Cart;
import dev.abraham.dreamshops.model.CartItem;
import dev.abraham.dreamshops.repository.CartItemRepository;
import dev.abraham.dreamshops.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public Cart getCart(Long Id) {
        Cart cart = cartRepository.findById(Id)
                .orElseThrow(()->new CartNotFound("Cart Not Found"));
        BigDecimal total = cart.getTotalAmount();
        cart.setTotalAmount(total);
        return cartRepository.save(cart);
    }

    public void clearCart(Long Id) {
        Cart cart = getCart(Id);
        cartItemRepository.deleteAllByCartId(Id);
        cart.getItems().clear();
        cartRepository.deleteById(Id);
    }

    public BigDecimal getTotalPrice(Long Id) {
        Cart cart = getCart(Id);
        return cart.getItems()
                .stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
