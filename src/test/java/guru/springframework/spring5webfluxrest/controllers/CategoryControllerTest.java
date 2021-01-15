package guru.springframework.spring5webfluxrest.controllers;

import guru.springframework.spring5webfluxrest.domain.Category;
import guru.springframework.spring5webfluxrest.repositories.CategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class CategoryControllerTest {
    WebTestClient webTestClient;
    CategoryRepository categoryRepository;
    CategoryController categoryController;


    @Before
    public void setUp() throws Exception {
        categoryRepository = Mockito.mock(CategoryRepository.class);
        categoryController = new CategoryController(categoryRepository);
        webTestClient = WebTestClient.bindToController(categoryController).build();
    }

    @Test
    public void getAllCategories() {
        BDDMockito.given(categoryRepository.findAll()).willReturn(Flux.just(Category.builder().description("cat1").build(),
                Category.builder().description("cat2").build()));

        webTestClient.get()
                .uri("/api/v1/categories")
                .exchange()
                .expectBodyList(Category.class)
                .hasSize(2);
    }

    @Test
    public void getCategory() {
        BDDMockito.given(categoryRepository.findById("someid"))
                .willReturn(Mono.just((Category.builder().description("cat3").build())));

        webTestClient.get()
                .uri("/api/v1/categories/someid")
                .exchange()
                .expectBody(Category.class);
    }

    @Test
    public void updateCategory() {
        BDDMockito.given(categoryRepository.save(any(Category.class)))
                .willReturn(Mono.just(Category.builder().build()));

        Mono<Category> catToYodate = Mono.just(Category.builder().build());

        webTestClient.put()
                .uri("/api/v1/categories/abvc")
                .body(catToYodate, Category.class)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    public void patchWithChangeCategory() {
        Category category = Category.builder().id("bipin").description("hello").build();
            BDDMockito.given(categoryRepository.findById(anyString()))
                .willReturn(Mono.just(category));

        Category category1 = Category.builder().id("bipin").description("new hello").build();

        BDDMockito.given(categoryRepository.save(category1)).willReturn(Mono.just(category1));

        webTestClient.patch()
                .uri("/api/v1/categories/bipin")
                .body(Mono.just(category1),Category.class)
                .exchange()
                .expectStatus()
                .isOk();

        verify(categoryRepository).save(any());
    }

    @Test
    public void patchWithNOChangeCategory() {
        Category category = Category.builder().id("bipin").description("hello").build();
        BDDMockito.given(categoryRepository.findById(anyString()))
                .willReturn(Mono.just(category));

        Category category1 = Category.builder().id("bipin").description("hello").build();

        BDDMockito.given(categoryRepository.save(category1)).willReturn(Mono.just(category1));

        webTestClient.patch()
                .uri("/api/v1/categories/bipin")
                .body(Mono.just(category1),Category.class)
                .exchange()
                .expectStatus()
                .isOk();

        verify(categoryRepository,never()).save(any());
    }

    @Test
    public void newCategory() {
        BDDMockito.given(categoryRepository.saveAll(any(Publisher.class)))
                .willReturn(Flux.just(Category.builder().build()));

        webTestClient.post()
                .uri("/api/v1/categories")
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    public void deleteCategory() {
        BDDMockito.given(categoryRepository.deleteById(anyString())).willReturn(Mono.empty());

        webTestClient
                .delete()
                .uri("/api/v1/categories/bipin")
                .exchange()
                .expectStatus()
                .isOk();

    }
}