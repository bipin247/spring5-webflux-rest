package guru.springframework.spring5webfluxrest.controllers;

import guru.springframework.spring5webfluxrest.domain.Vendor;
import guru.springframework.spring5webfluxrest.repositories.VendorRepository;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class VendorController {

    private final VendorRepository vendorRepository;

    public VendorController(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @GetMapping("/api/v1/vendors")
    @ResponseStatus(HttpStatus.OK)
    Flux<Vendor> getAllVendors(){
        return vendorRepository.findAll();
    }

    @GetMapping("/api/v1/vendors/{id}")
    @ResponseStatus(HttpStatus.OK)
    Mono<Vendor> getVendor(@PathVariable String id){
        return vendorRepository.findById(id);
    }

    @PostMapping("/api/v1/vendors")
    @ResponseStatus(HttpStatus.CREATED)
    Mono<Void> saveAll(@RequestBody Publisher publisher){
       return  vendorRepository.saveAll(publisher).then();
    }

    @PutMapping("/api/v1/vendors/{id}")
    @ResponseStatus(HttpStatus.OK)
    Mono<Vendor> updateVendor(@PathVariable String id, @RequestBody Vendor vendor){
        vendor.setId(id);
        return vendorRepository.save(vendor);
    }

    @DeleteMapping("/api/v1/vendors/{id}")
    @ResponseStatus(HttpStatus.OK)
    void deleteVendor(@PathVariable String id){
        vendorRepository.deleteById(id);
    }

    @PatchMapping("/api/v1/vendors/{id}")
    @ResponseStatus(HttpStatus.OK)
    Mono<Vendor> patchVendor(@PathVariable String id, @RequestBody Vendor vendor){

        Vendor foundVendor = vendorRepository.findById(id).block();

        if(!foundVendor.getFirstName().equals(vendor.getFirstName())){
            foundVendor.setFirstName(vendor.getFirstName());

            return vendorRepository.save(foundVendor);
        }
        return Mono.just(foundVendor);

    }
}
