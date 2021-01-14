package guru.springframework.spring5webfluxrest.boostrap;


import guru.springframework.spring5webfluxrest.domain.Category;
import guru.springframework.spring5webfluxrest.domain.Vendor;
import guru.springframework.spring5webfluxrest.repositories.CategoryRepository;
import guru.springframework.spring5webfluxrest.repositories.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {

    private CategoryRepository categoryRepository;
    private VendorRepository vendorRepository;

    public Bootstrap(CategoryRepository categoryRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (categoryRepository.count().block() == 0) loadCategories();
        System.out.println("????????????");
        if (vendorRepository.count().block() == 0) loadVendors();
    }

    private void loadVendors() {
        vendorRepository.save(Vendor.builder()
                .firstName("Joe")
                .lastName("Buck").build()).block();

        vendorRepository.save(Vendor.builder()
                .firstName("Micheal")
                .lastName("Weston").build()).block();

        vendorRepository.save(Vendor.builder()
                .firstName("Jessie")
                .lastName("Waters").build()).block();

        vendorRepository.save(Vendor.builder()
                .firstName("Bill")
                .lastName("Nershi").build()).block();

        vendorRepository.save(Vendor.builder()
                .firstName("Jimmy")
                .lastName("Buffett").build()).block();

        System.out.println("Loaded Vendors: " + vendorRepository.count().block());

    }

    private void loadCategories() {
        //load data
        System.out.println("#### LOADING DATA ON BOOTSTRAP #####");

        categoryRepository.save(Category.builder()
                .description("Fruits").build()).block();

        categoryRepository.save(Category.builder()
                .description("Nuts").build()).block();

        categoryRepository.save(Category.builder()
                .description("Breads").build()).block();

        categoryRepository.save(Category.builder()
                .description("Meats").build()).block();

        categoryRepository.save(Category.builder()
                .description("Eggs").build()).block();

        System.out.println("Loaded Categories: " + categoryRepository.count().block());

    }
}
