package dev.abraham.dreamshops.service.product;

import dev.abraham.dreamshops.dto.ImageDTO;
import dev.abraham.dreamshops.dto.ProductDTO;
import dev.abraham.dreamshops.exceptions.ProductNotFoundException;
import dev.abraham.dreamshops.model.Category;
import dev.abraham.dreamshops.model.Image;
import dev.abraham.dreamshops.model.Product;
import dev.abraham.dreamshops.repository.CategoryRepository;
import dev.abraham.dreamshops.repository.ImageRepository;
import dev.abraham.dreamshops.repository.ProductRepository;
import dev.abraham.dreamshops.request.AddProductRequest;
import dev.abraham.dreamshops.request.ProductUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final ImageRepository imageRepository;

    public Product addProduct(AddProductRequest product) {
        Category category = Optional.ofNullable(categoryRepository.findByName(product.getCategory().getName()))
                .orElseGet(()->{
                    Category c=new Category(product.getCategory().getName());
                    return categoryRepository.save(c);
                });
        product.setCategory(category);
        return productRepository.save(createProduct(product, category));
    }

    private Product createProduct(AddProductRequest product, Category category) {
        return new Product(
                product.getName(),
                product.getBrand(),
                product.getPrice(),
                product.getInventory(),
                product.getDescription(),
                category
        );
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(()->new ProductNotFoundException("Product Not Found"));
    }

    public void deleteProductById(Long id) {
        productRepository.findById(id)
                .ifPresentOrElse(productRepository::delete,
                        ()->{throw new ProductNotFoundException("Product Not Found");});
    }

    public Product updateProduct(ProductUpdateRequest product, Long id) {
        return productRepository.findById(id)
                .map(existingProduct->updateExistingProduct(existingProduct, product))
                .map(productRepository::save)
                .orElseThrow(()->new ProductNotFoundException("Product Not Found"));
    }

    private Product updateExistingProduct(Product existingProduct, ProductUpdateRequest request) {
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());
        existingProduct.setDescription(request.getDescription());
        Category category = categoryRepository.findByName(request.getCategory().getName());
        existingProduct.setCategory(category);
        return existingProduct;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> getProductsByCategory(String categoryId) {
        return productRepository.findByCategoryName(categoryId);
    }

    public List<Product> getProductsByBrand(String brandId) {
        return productRepository.findByBrand(brandId);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category, brand);
    }

    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    public List<Product> getProductsByBrandAndName(String brandId, String name) {
        return productRepository.findByBrandAndName(brandId, name);
    }

    public Long countProductsByBrandAndName(String brandId, String name) {
        return productRepository.countByBrandAndName(brandId, name);
    }

    public List<ProductDTO> getCastProducts(List<Product> products) {
        return products.stream().map(this::castToProductDTO).toList();
    }

    public ProductDTO castToProductDTO(Product product) {
        ProductDTO dto= modelMapper.map(product, ProductDTO.class);
        List<Image> images=imageRepository.findByProductId(dto.getId());
        List<ImageDTO> imageDTO=images.stream()
                .map(image->modelMapper.map(image, ImageDTO.class))
                .toList();
        dto.setImages(imageDTO);
        return dto;
    }
}
