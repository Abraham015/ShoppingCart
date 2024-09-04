package dev.abraham.dreamshops.service.product;

import dev.abraham.dreamshops.dto.ProductDTO;
import dev.abraham.dreamshops.model.Product;
import dev.abraham.dreamshops.request.product.AddProductRequest;
import dev.abraham.dreamshops.request.product.ProductUpdateRequest;

import java.util.List;

public interface IProductService {
    Product addProduct(AddProductRequest product);
    Product getProductById(Long id);
    void deleteProductById(Long id);
    Product updateProduct(ProductUpdateRequest product, Long id);
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String categoryId);
    List<Product> getProductsByBrand(String brandId);
    List<Product> getProductsByCategoryAndBrand(String category, String brand);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByBrandAndName(String brandId, String name);
    Long countProductsByBrandAndName(String brandId, String name);

    List<ProductDTO> getCastProducts(List<Product> products);

    ProductDTO castToProductDTO(Product product);
}