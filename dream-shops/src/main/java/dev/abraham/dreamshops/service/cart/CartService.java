package dev.abraham.dreamshops.service.cart;

import dev.abraham.dreamshops.exceptions.CartNotFound;
import dev.abraham.dreamshops.model.Cart;
import dev.abraham.dreamshops.model.CartItem;
import dev.abraham.dreamshops.model.User;
import dev.abraham.dreamshops.repository.CartItemRepository;
import dev.abraham.dreamshops.repository.CartRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final AtomicLong cartIdGenerator=new AtomicLong(0);

    public Cart getCart(Long Id) {
        Cart cart = cartRepository.findById(Id)
                .orElseThrow(()->new CartNotFound("Cart Not Found"));
        BigDecimal total = cart.getTotalAmount();
        cart.setTotalAmount(total);
        return cartRepository.save(cart);
    }

    @Transactional
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

    public Cart initializeNewCart(User user) {
        return Optional.ofNullable(getCartByUserId(user.getId()))
                .orElseGet(()->{
                    Cart cart = new Cart();
                    cart.setUser(user);
                    return cartRepository.save(cart);
                });
    }

    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }
}
