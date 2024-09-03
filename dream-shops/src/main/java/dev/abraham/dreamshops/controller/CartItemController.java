package dev.abraham.dreamshops.controller;

import dev.abraham.dreamshops.exceptions.CartNotFound;
import dev.abraham.dreamshops.response.APIResponse;
import dev.abraham.dreamshops.service.cartitem.ICartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/cartItem")
public class CartItemController {
    private final ICartItemService cartItemService;

    @PostMapping("/add")
    public ResponseEntity<APIResponse> addItemToCart(@RequestParam Long cartId, @RequestParam Long productId, @RequestParam Integer quantity) {
        try {
            cartItemService.addItem(cartId, productId, quantity);
            return ResponseEntity.ok(new APIResponse("Item added sucessfully", null));
        } catch (CartNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<APIResponse> deleteItemFromCart(@RequestParam Long cartId, @RequestParam Long productId) {
        try {
            cartItemService.removeItem(cartId, productId);
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
