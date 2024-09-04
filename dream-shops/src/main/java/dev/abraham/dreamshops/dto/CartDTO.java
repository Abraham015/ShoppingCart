package dev.abraham.dreamshops.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class CartDTO {
    private Long cartId;
    private Set<CartItemDTO> items;
    private BigDecimal totalPrice;
}
