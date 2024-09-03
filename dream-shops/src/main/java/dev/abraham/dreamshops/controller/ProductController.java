package dev.abraham.dreamshops.controller;

import dev.abraham.dreamshops.exceptions.ProductNotFoundException;
import dev.abraham.dreamshops.model.Product;
import dev.abraham.dreamshops.request.AddProductRequest;
import dev.abraham.dreamshops.request.ProductUpdateRequest;
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
        return ResponseEntity.ok(new APIResponse("Success", products));
    }

    @GetMapping("/{product_id}")
    public ResponseEntity<APIResponse> getProductById(@PathVariable Long product_id) {
        try{
            Product product=productService.getProductById(product_id);
            return ResponseEntity.ok(new APIResponse("Sucess", product));
        }catch (ProductNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/get-by-category/{category}")
    public ResponseEntity<APIResponse> getProductsByCategory(@PathVariable String category) {
        try {
            List<Product> products=productService.getProductsByCategory(category);
            if(products.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse("Category not found", null));
            }
            return ResponseEntity.ok(new APIResponse("Sucess", products));
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
            return ResponseEntity.ok(new APIResponse("Sucess", products));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new APIResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/get-by-brand-and-category/")
    public ResponseEntity<APIResponse> getProductsByCategoryAndBrand(@RequestParam String category, @RequestParam String brand) {
        try{
            List<Product> products=productService.getProductsByBrandAndName(category, brand);
            if(products.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(null, products));
            }
            return ResponseEntity.ok(new APIResponse("Sucess", products));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new APIResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/get-by-name/{name}")
    public ResponseEntity<APIResponse> getProductsByName(@PathVariable String name) {
        try {
            List<Product> products=productService.getProductsByName(name);
            if(products.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse("Product not found", null));
            }
            return ResponseEntity.ok(new APIResponse("Sucess", products));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new APIResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/get-by-brand")
    public ResponseEntity<APIResponse> getProductsByBrand(@RequestParam String brand) {
        try {
            List<Product> products=productService.getProductsByBrand(brand);
            if(products.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse("Product not found", null));
            }
            return ResponseEntity.ok(new APIResponse("Sucess", products));
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
