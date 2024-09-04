package dev.abraham.dreamshops.repository;

import dev.abraham.dreamshops.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryName(String categoryId);
    List<Product> findByBrand(String brandId);
    List<Product> findByCategoryNameAndBrand(String category, String brand);
    List<Product> findByName(String name);
    List<Product> findByBrandAndName(String brandId, String name);
    Long countByBrandAndName(String brandId, String name);
    boolean existsByNameAndBrand(String name, String brand);
}
