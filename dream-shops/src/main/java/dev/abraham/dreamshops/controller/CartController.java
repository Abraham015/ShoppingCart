package dev.abraham.dreamshops.controller;

import dev.abraham.dreamshops.exceptions.CartNotFound;
import dev.abraham.dreamshops.model.Cart;
import dev.abraham.dreamshops.response.APIResponse;
import dev.abraham.dreamshops.service.cart.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/carts")
public class CartController {
    private final ICartService cartService;

    @GetMapping("/my-cart/{cartId}")
    public ResponseEntity<APIResponse> getCart(@PathVariable Long cartId){
        try{
            Cart cart = cartService.getCart(cartId);
            return ResponseEntity.ok(new APIResponse("Success", cart));
        }catch (CartNotFound e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete/{cartId}")
    public ResponseEntity<APIResponse> clearCart(@PathVariable Long cartId){
        try {
            cartService.clearCart(cartId);
            return ResponseEntity.ok(new APIResponse("Successfully cleared", null));
        } catch (CartNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/total/{cartId}")
    public ResponseEntity<APIResponse> getTotalAmount(@PathVariable Long cartId){
        try {
            BigDecimal total=cartService.getTotalPrice(cartId);
            return ResponseEntity.ok(new APIResponse("Success", total));
        } catch (CartNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }
}
