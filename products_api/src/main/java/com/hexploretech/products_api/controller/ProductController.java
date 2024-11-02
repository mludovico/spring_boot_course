package com.hexploretech.products_api.controller;

import com.hexploretech.products_api.model.Product;
import com.hexploretech.products_api.repository.ProductRepository;
import jakarta.annotation.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {
    private ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @PostMapping
    public Product save(@RequestBody Product product) {
        System.out.println("Product saved: " + product);
        var uuid = UUID.randomUUID().toString();
        product.setId(uuid);
        productRepository.save(product);
        return product;
    }

    @GetMapping
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable String id) {
        return productRepository.findById(id).orElse(null);
    }

    @PatchMapping("/{id}")
    public Product update(@PathVariable String id, @RequestBody Product product) {
        var existingProduct = productRepository.findById(id).orElse(null);
        if (existingProduct == null) {
            return null;
        }
        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        productRepository.save(existingProduct);
        return existingProduct;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        productRepository.deleteById(id);
    }

    @GetMapping("/search")
    public List<Product> search(@RequestParam("name") String name) {
        return productRepository.findByName(name);
    }
}
