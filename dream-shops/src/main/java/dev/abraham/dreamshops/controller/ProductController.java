package dev.abraham.dreamshops.controller;

import dev.abraham.dreamshops.dto.ProductDTO;
import dev.abraham.dreamshops.exceptions.ProductNotFoundException;
import dev.abraham.dreamshops.model.Product;
import dev.abraham.dreamshops.request.product.AddProductRequest;
import dev.abraham.dreamshops.request.product.ProductUpdateRequest;
import dev.abraham.dreamshops.response.APIResponse;
import dev.abraham.dreamshops.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {
    private final IProductService productService;

    @GetMapping("/all")
    public ResponseEntity<APIResponse> getAllProducts() {
        List<Product> products=productService.getAllProducts();
        List<ProductDTO> dto=productService.getCastProducts(products);
        return ResponseEntity.ok(new APIResponse("Success", dto));
    }

    @GetMapping("/{product_id}")
    public ResponseEntity<APIResponse> getProductById(@PathVariable Long product_id) {
        try{
            Product product=productService.getProductById(product_id);
            ProductDTO dto=productService.castToProductDTO(product);
            return ResponseEntity.ok(new APIResponse("Sucess", dto));
        }catch (ProductNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/get-by-category")
    public ResponseEntity<APIResponse> getProductsByCategory(@RequestParam String category) {
        try {
            List<Product> products=productService.getProductsByCategory(category);
            if(products.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse("Category not found", null));
            }
            List<ProductDTO> dto=productService.getCastProducts(products);
            return ResponseEntity.ok(new APIResponse("Sucess", dto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new APIResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/get-by-brand-and-name/")
    public ResponseEntity<APIResponse> getProductsByBrandAndName(@RequestParam String brand, @RequestParam String name) {
        try{
            List<Product> products=productService.getProductsByBrandAndName(brand, name);
            if(products.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(null, products));
            }
            List<ProductDTO> dto=productService.getCastProducts(products);
            return ResponseEntity.ok(new APIResponse("Sucess", dto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new APIResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/get-by-brand-and-category/")
    public ResponseEntity<APIResponse> getProductsByCategoryAndBrand(@RequestParam String category, @RequestParam String brand) {
        try{
            List<Product> products=productService.getProductsByCategoryAndBrand (category, brand);
            if(products.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(null, products));
            }
            List<ProductDTO> dto=productService.getCastProducts(products);
            return ResponseEntity.ok(new APIResponse("Sucess", dto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new APIResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/get-by-name")
    public ResponseEntity<APIResponse> getProductsByName(@RequestParam String name) {
        try {
            List<Product> products=productService.getProductsByName(name);
            if(products.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse("Product not found", null));
            }
            List<ProductDTO> dto=productService.getCastProducts(products);
            return ResponseEntity.ok(new APIResponse("Sucess", dto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new APIResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/get-by-brand")
    public ResponseEntity<APIResponse> getProductsByBrand(@RequestParam String brand) {
        try {
            List<Product> products=productService.getProductsByBrand(brand);
            if(products.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse("Brand not found", null));
            }
            List<ProductDTO> dto=productService.getCastProducts(products);
            return ResponseEntity.ok(new APIResponse("Sucess", dto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new APIResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/count")
    public ResponseEntity<APIResponse> countProductsByBrandAndName(@RequestParam String brand, @RequestParam String name) {
        try{
            Long c=productService.countProductsByBrandAndName(brand, name);
            return ResponseEntity.ok(new APIResponse("Sucess", c));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new APIResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<APIResponse> addProduct(@RequestBody AddProductRequest product) {
        try{
            Product p=productService.addProduct(product);
            return ResponseEntity.ok(new APIResponse("Product added sucessfully", p));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new APIResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/update/{product_id}")
    public ResponseEntity<APIResponse> updateProduct(@RequestBody ProductUpdateRequest product, @PathVariable Long product_id) {
        try {
            Product p=productService.updateProduct(product, product_id);
            return ResponseEntity.ok(new APIResponse("Product updated successfully", p));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new APIResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete/{product_id}")
    public ResponseEntity<APIResponse> deleteProduct(@PathVariable Long product_id) {
        try{
            productService.deleteProductById(product_id);
            return ResponseEntity.ok(new APIResponse("Product deleted successfully", product_id));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new APIResponse(e.getMessage(), null));
        }
    }

}
