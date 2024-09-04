package dev.abraham.dreamshops.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemDTO {
    private Long id;
    private int quantity;
    private BigDecimal unitPrice;
    private ProductDTO productDTO;
}
