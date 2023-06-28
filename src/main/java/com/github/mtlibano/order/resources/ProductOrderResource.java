package com.github.mtlibano.order.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.mtlibano.order.domain.ProductOrder;
import com.github.mtlibano.order.domain.dto.ProductOrderDTO;
import com.github.mtlibano.order.services.OrderService;
import com.github.mtlibano.order.services.ProductOrderService;
import com.github.mtlibano.order.services.ProductService;

@RestController
@RequestMapping(value = "/product-order")
public class ProductOrderResource {
	
	@Autowired
	ProductOrderService service;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	OrderService orderService;
	
	@PostMapping
    public ResponseEntity<ProductOrderDTO> insert(@RequestBody ProductOrderDTO productOrderDTO) {
        return ResponseEntity.ok(service.insert(new ProductOrder(
        		productOrderDTO,
        		productService.findById(productOrderDTO.getProductId()),
        		orderService.findById(productOrderDTO.getOrderId()))).toDTO());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductOrderDTO> update(@PathVariable Integer id, @RequestBody ProductOrderDTO productOrderDTO) {
    	ProductOrder productOrder = new ProductOrder(
        		productOrderDTO,
        		productService.findById(productOrderDTO.getProductId()),
        		orderService.findById(productOrderDTO.getOrderId()));
    	productOrder.setId(id);
    	productOrder = service.update(productOrder);
        return ResponseEntity.ok(productOrder.toDTO());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductOrderDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id).toDTO());
    }

    @GetMapping
    public ResponseEntity<List<ProductOrderDTO>> listAll() {
        return ResponseEntity.ok(service.listAll().stream().map(ProductOrder::toDTO).toList());
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ProductOrderDTO>> findByProduct(@PathVariable Integer productId) {
        return ResponseEntity.ok(service.findByProduct(productService.findById(productId)).stream().map(ProductOrder::toDTO).toList());
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<ProductOrderDTO>> findByOrder(@PathVariable Integer orderId) {
        return ResponseEntity.ok(service.findByOrder(orderService.findById(orderId)).stream().map(ProductOrder::toDTO).toList());
    }

}