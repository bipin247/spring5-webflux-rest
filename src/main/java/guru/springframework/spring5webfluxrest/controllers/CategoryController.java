package guru.springframework.spring5webfluxrest.controllers;

import guru.springframework.spring5webfluxrest.domain.Category;
import guru.springframework.spring5webfluxrest.repositories.CategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(CategoryController.BASE_URL)
public class CategoryController {

    public static final String BASE_URL = "/api/v1/categories";

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    Flux<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    Mono<Category> getCategory(@PathVariable String id) {
        return categoryRepository.findById(id);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    Mono<Category> updateCategory(@PathVariable String id , @RequestBody Category category) {
        return categoryRepository.save(category);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    Mono<Category> patchCategory(@PathVariable String id, @RequestBody Category category) {
        return categoryRepository.save(category);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Mono<Category> newCategory(@RequestBody Category category) {
        return categoryRepository.save(category);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    void deleteCategory(@PathVariable String id) {
        categoryRepository.deleteById(id);
    }

}
