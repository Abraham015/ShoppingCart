package dev.abraham.dreamshops.service.cartitem;

import dev.abraham.dreamshops.exceptions.ProductNotFoundException;
import dev.abraham.dreamshops.model.Cart;
import dev.abraham.dreamshops.model.CartItem;
import dev.abraham.dreamshops.model.Product;
import dev.abraham.dreamshops.repository.CartItemRepository;
import dev.abraham.dreamshops.repository.CartRepository;
import dev.abraham.dreamshops.service.cart.ICartService;
import dev.abraham.dreamshops.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService {
    private final CartItemRepository cartItemRepository;
    private final ICartService cartService;
    private final CartRepository cartRepository;
    private final IProductService productService;

    public void addItem(Long cartId, Long productId, int quantity) {
        //1. Get the cart
        //2. Get the product
        //3. Check if the product already in the cart
        //4. If yes, then increase the quantity
        //5. If no, then initialize a new cart item entry
        Cart cart=cartService.getCart(cartId);
        Product product=productService.getProductById(productId);
        CartItem cartItem=cart.getItems()
                .stream()
                .filter(item->item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(new CartItem());
        if(cartItem.getId()==null){
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(product.getPrice());
        }else {
            cartItem.setQuantity(cartItem.getQuantity()+quantity);
        }
        cartItem.setTotalPrice();
        cart.addItem(cartItem);
        cartItemRepository.save(cartItem);
        cartRepository.save(cart);
    }

    public void removeItem(Long cartId, Long productId) {
        Cart cart=cartService.getCart(cartId);
        CartItem item=getCartItem(cartId, productId);
        cart.removeItem(item);
        cartRepository.save(cart);
    }

    public void updateItemQuantity(Long cartId, Long productId, int quantity) {
        Cart cart=cartService.getCart(cartId);
        cart.getItems()
                .stream()
                .filter(item->item.getProduct().getId().equals(productId))
                .findFirst()
                .ifPresentOrElse(item->{
                    item.setQuantity(quantity);
                    item.setUnitPrice(item.getProduct().getPrice());
                    item.setTotalPrice();
                }, ()->{throw new ProductNotFoundException("Product Not Found");});
        BigDecimal totalAmount=cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        cartRepository.save(cart);
    }

    public CartItem getCartItem(Long cartId, Long productId) {
        Cart cart=cartService.getCart(cartId);
        return cart.getItems()
                .stream()
                .filter(it->it.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(()->new ProductNotFoundException("Item Not Found"));
    }
}
