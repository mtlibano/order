package com.github.mtlibano.order.resources;

import com.github.mtlibano.order.domain.Product;
import com.github.mtlibano.order.domain.dto.ProductDTO;
import com.github.mtlibano.order.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping(value = "/product")
public class ProductResource {

    @Autowired
    ProductService service;

    @PostMapping
    public ResponseEntity<ProductDTO> insert(@RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(service.insert(new Product(productDTO)).toDTO());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable Integer id, @RequestBody ProductDTO productDTO) {
        Product product = new Product(productDTO);
        product.setId(id);
        product = service.update(product);
        return ResponseEntity.ok(product.toDTO());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id).toDTO());
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> listAll() {
        return ResponseEntity.ok(service.listAll().stream().map(Product::toDTO).toList());
    }

    @GetMapping("/description/{description}")
    public ResponseEntity<List<ProductDTO>> findByDescriptionIgnoreCase(@PathVariable String description) {
        return ResponseEntity.ok(service.findByDescriptionIgnoreCase(description).stream().map(Product::toDTO).toList());
    }

    @GetMapping("/price/{price}")
    public ResponseEntity<List<ProductDTO>> findByPrice(@PathVariable BigDecimal price) {
        return ResponseEntity.ok(service.findByPrice(price).stream().map(Product::toDTO).toList());
    }

    @GetMapping("/price/{initialPrice}/{finalPrice}")
    public ResponseEntity<List<ProductDTO>> findByPriceBetween(@PathVariable BigDecimal initialPrice, @PathVariable BigDecimal finalPrice) {
        return ResponseEntity.ok(service.findByPriceBetween(initialPrice, finalPrice).stream().map(Product::toDTO).toList());
    }
    
    @GetMapping("/barcode/{barcode}")
    public ResponseEntity<List<ProductDTO>> findByBarcode(@PathVariable String barcode) {
        return ResponseEntity.ok(service.findByBarcode(barcode).stream().map(Product::toDTO).toList());
    }

}