package dev.abraham.dreamshops.service.product;

import dev.abraham.dreamshops.model.Product;
import dev.abraham.dreamshops.request.AddProductRequest;
import dev.abraham.dreamshops.request.ProductUpdateRequest;

import java.util.List;

public interface IProductService {
    Product addProduct(AddProductRequest product);
    Product getProductById(Long id);
    void deleteProductById(Long id);
    Product updateProduct(ProductUpdateRequest product, Long id);
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String categoryId);
    List<Product> getProductsByBrand(String brandId);
    List<Product> getProductsByCategoryAndBrand(String categoryId, String brandId);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByBrandAndName(String brandId, String name);
    Long countProductsByBrandAndName(String brandId, String name);
}