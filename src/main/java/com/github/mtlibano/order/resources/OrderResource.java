package com.github.mtlibano.order.resources;

import com.github.mtlibano.order.domain.Order;
import com.github.mtlibano.order.domain.dto.OrderDTO;
import com.github.mtlibano.order.services.ClientService;
import com.github.mtlibano.order.services.OrderService;
import com.github.mtlibano.order.services.PaymentService;
import com.github.mtlibano.order.services.RatingService;
import com.github.mtlibano.order.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/order")
public class OrderResource {

    @Autowired
    OrderService service;

    @Autowired
    ClientService clientService;

    @Autowired
    PaymentService paymentService;

    @Autowired
    RatingService ratingService;

    @PostMapping
    public ResponseEntity<OrderDTO> insert(@RequestBody OrderDTO orderDTO) {
        return ResponseEntity.ok(service.insert(new Order(
                orderDTO,
                clientService.findById(orderDTO.getClientId()),
                paymentService.findById(orderDTO.getPaymentId()),
                ratingService.findById(orderDTO.getRatingId()))).
                toDTO());
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> update(@PathVariable Integer id, @RequestBody OrderDTO orderDTO) {
        Order order = new Order(
                orderDTO,
                clientService.findById(orderDTO.getClientId()),
                paymentService.findById(orderDTO.getPaymentId()),
                ratingService.findById(orderDTO.getRatingId()));
        order.setId(id);
        order = service.update(order);
        return ResponseEntity.ok(order.toDTO());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id).toDTO());
    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> listAll() {
        return ResponseEntity.ok(service.listAll().stream().map(Order::toDTO).toList());
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<OrderDTO>> findByDate(@RequestParam String date) {
        return ResponseEntity.ok(service.findByDate(DateUtils.strToZonedDateTime(date)).stream().map(Order::toDTO).toList());
    }

    @GetMapping("/date/{initialDate}/{finalDate}")
    public ResponseEntity<List<OrderDTO>> findByDateBetween(@RequestParam String initialDate, @RequestParam String finalDate) {
        return ResponseEntity.ok(service.findByDateBetween(DateUtils.strToZonedDateTime(initialDate), DateUtils.strToZonedDateTime(finalDate)).stream().map(Order::toDTO).toList());
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<OrderDTO>> findByClient(@PathVariable Integer clientId) {
        return ResponseEntity.ok(service.findByClient(clientService.findById(clientId)).stream().map(Order::toDTO).toList());
    }

    @GetMapping("/payment/{paymentId}")
    public ResponseEntity<List<OrderDTO>> findByPayment(@PathVariable Integer paymentId) {
        return ResponseEntity.ok(service.findByPayment(paymentService.findById(paymentId)).stream().map(Order::toDTO).toList());
    }

    @GetMapping("/rating/{ratingId}")
    public ResponseEntity<List<OrderDTO>> findByRating(@PathVariable Integer ratingId) {
        return ResponseEntity.ok(service.findByRating(ratingService.findById(ratingId)).stream().map(Order::toDTO).toList());
    }

}