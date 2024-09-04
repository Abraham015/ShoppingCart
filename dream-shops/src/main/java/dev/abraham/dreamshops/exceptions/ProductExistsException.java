package dev.abraham.dreamshops.exceptions;

public class ProductExistsException extends RuntimeException {
    public ProductExistsException(String message) {
        super(message);
    }
}
