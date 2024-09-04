package dev.abraham.dreamshops.dto;

import dev.abraham.dreamshops.model.Cart;
import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<OrderDTO> orders;
    private Cart cart;
}
