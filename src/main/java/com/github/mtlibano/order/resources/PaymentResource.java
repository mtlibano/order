package com.github.mtlibano.order.resources;

import com.github.mtlibano.order.domain.Payment;
import com.github.mtlibano.order.domain.dto.PaymentDTO;
import com.github.mtlibano.order.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/payment")
public class PaymentResource {

    @Autowired
    PaymentService service;

    @PostMapping
    public ResponseEntity<PaymentDTO> insert(@RequestBody PaymentDTO paymentDTO) {
        return ResponseEntity.ok(service.insert(new Payment(paymentDTO)).toDTO());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentDTO> update(@PathVariable Integer id, @RequestBody PaymentDTO paymentDTO) {
        Payment payment = new Payment(paymentDTO);
        payment.setId(id);
        payment = service.update(payment);
        return ResponseEntity.ok(payment.toDTO());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id).toDTO());
    }

    @GetMapping
    public ResponseEntity<List<PaymentDTO>> listAll() {
        return ResponseEntity.ok(service.listAll().stream().map(Payment::toDTO).toList());
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<PaymentDTO>> findByTypeIgnoreCase(@PathVariable String type) {
        return ResponseEntity.ok(service.findByTypeIgnoreCase(type).stream().map(Payment::toDTO).toList());
    }

}