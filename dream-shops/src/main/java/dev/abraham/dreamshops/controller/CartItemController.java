package dev.abraham.dreamshops.controller;

import dev.abraham.dreamshops.dto.UserDTO;
import dev.abraham.dreamshops.exceptions.CartNotFound;
import dev.abraham.dreamshops.exceptions.UserNotFound;
import dev.abraham.dreamshops.model.Cart;
import dev.abraham.dreamshops.model.User;
import dev.abraham.dreamshops.response.APIResponse;
import dev.abraham.dreamshops.service.cart.ICartService;
import dev.abraham.dreamshops.service.cartitem.ICartItemService;
import dev.abraham.dreamshops.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/cartItem")
public class CartItemController {
    private final ICartItemService cartItemService;
    private final ICartService cartService;
    private final IUserService userService;

    @PostMapping("/add")
    public ResponseEntity<APIResponse> addItemToCart(@RequestParam Long productId, @RequestParam Integer quantity) {
        try {
            User user=userService.getUserById(1L);
            Cart cart=cartService.initializeNewCart(user);
            cartItemService.addItem(cart.getId(), productId, quantity);
            return ResponseEntity.ok(new APIResponse("Item added sucessfully", null));
        } catch (CartNotFound | UserNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<APIResponse> deleteItemFromCart(@RequestParam Long cartId, @RequestParam Long itemId) {
        try {
            cartItemService.removeItem(cartId, itemId);
            return ResponseEntity.ok(new APIResponse("Item deleted successfully", null));
        } catch (CartNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<APIResponse> updateItemQuanitty(@RequestParam Long cartId, @RequestParam Long itemId, @RequestParam Integer quantity) {
        try {
            cartItemService.updateItemQuantity(cartId, itemId, quantity);
            return ResponseEntity.ok(new APIResponse("Item updated successfully", null));
        } catch (CartNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }

    }
}
