package guru.springframework.spring5webfluxrest.controllers;

import guru.springframework.spring5webfluxrest.repositories.CategoryRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.mockito.Mockito;
import org.springframework.test.web.reactive.server.WebTestClient;

class CategoryControllerTest {
    WebTestClient webTestClient;
    CategoryRepository categoryRepository;
    CategoryController categoryController;

    @BeforeAll
    void setUp() {
        categoryRepository = Mockito.mock(CategoryRepository.class);
        categoryController = new CategoryController(categoryRepository);
        webTestClient = WebTestClient.bindToController(categoryController).build();
    }

    @Test
    void getAllCategories() {
//        BDDMockito.given(categoryRepository.)
    }
}