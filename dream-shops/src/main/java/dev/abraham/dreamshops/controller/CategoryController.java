package dev.abraham.dreamshops.controller;

import dev.abraham.dreamshops.exceptions.CategoryExistsException;
import dev.abraham.dreamshops.exceptions.CategoryNotFoundException;
import dev.abraham.dreamshops.model.Category;
import dev.abraham.dreamshops.response.APIResponse;
import dev.abraham.dreamshops.service.category.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {
    private final ICategoryService categoryService;

    @GetMapping("/all")
    public ResponseEntity<APIResponse> getAllCategories() {
        try{
            List<Category> categories=categoryService.findAllCategories();
            return ResponseEntity.ok(new APIResponse("Found", categories));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new APIResponse("Error", e.getMessage()));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<APIResponse> addCategory(@RequestBody Category category) {
        try{
            Category theCategory=categoryService.addCategory(category);
            return ResponseEntity.ok(new APIResponse("Added", theCategory));
        } catch (CategoryExistsException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new APIResponse("Category already exists", e.getMessage()));
        }
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<APIResponse> getCategoryById(@PathVariable Long id) {
        try{
            Category c=categoryService.findCategoryById(id);
            return ResponseEntity.ok(new APIResponse("Found", c));
        }catch (CategoryNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse("Category not found", e.getMessage()));
        }
    }

    @GetMapping("/category/{name}")
    public ResponseEntity<APIResponse> getCategoryByName(@PathVariable String name) {
        try{
            Category c=categoryService.findCategoryByName(name);
            return ResponseEntity.ok(new APIResponse("Found", c));
        }catch (CategoryNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse("Category not found", e.getMessage()));
        }
    }

    @DeleteMapping("/category/{id}")
    public ResponseEntity<APIResponse> deleteCategory(@PathVariable Long id) {
        try{
            categoryService.deleteCategory(id);
            return ResponseEntity.ok(new APIResponse("Delete Sucess", null));
        }catch (CategoryNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse("Error", e.getMessage()));
        }
    }

    @PutMapping("/category/{id}")
    public ResponseEntity<APIResponse> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        try{
            Category updated=categoryService.updateCategory(category,id);
            return ResponseEntity.ok(new APIResponse("Delete Sucess", updated));
        }catch (CategoryNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse("Error", e.getMessage()));
        }
    }
}


