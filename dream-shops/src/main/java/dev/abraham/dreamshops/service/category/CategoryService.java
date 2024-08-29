package dev.abraham.dreamshops.service.category;

import dev.abraham.dreamshops.exceptions.CategoryExistsException;
import dev.abraham.dreamshops.exceptions.CategoryNotFoundException;
import dev.abraham.dreamshops.model.Category;
import dev.abraham.dreamshops.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
    private final CategoryRepository categoryRepository;

    public Category findCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(()->new CategoryNotFoundException("Category Not Found"));
    }

    public Category findCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    public Category addCategory(Category category) {
        return Optional.of(category).filter(c->!categoryRepository.existsByName(c.getName()))
                .map(categoryRepository::save)
                .orElseThrow(()->new CategoryExistsException(category.getName()+" already exists"));
    }

    public Category updateCategory(Category category, Long id) {
        return Optional.ofNullable(findCategoryById(id))
                .map(oldCategory->{
                    oldCategory.setName(category.getName());
                    return categoryRepository.save(oldCategory);
                })
                .orElseThrow(()->new CategoryNotFoundException("Category Not Found"));
    }

    public void deleteCategory(Long id) {
        categoryRepository.findById(id)
                .ifPresentOrElse(categoryRepository::delete,
                        () -> {throw new CategoryNotFoundException("Category Not Found");});
    }
}
