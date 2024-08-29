package dev.abraham.dreamshops.repository;

import dev.abraham.dreamshops.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryName(String categoryId);
    List<Product> findByBrandName(String brandId);
    List<Product> findByCategoryAndBrand(String categoryId, String brandId);
    List<Product> findByName(String name);
    List<Product> findByBrandAndName(String brandId, String name);
    Long countByBrandAndName(String brandId, String name);
}
