package com.github.mtlibano.order.resources;

import com.github.mtlibano.order.domain.PhoneNumber;
import com.github.mtlibano.order.domain.dto.PhoneNumberDTO;
import com.github.mtlibano.order.services.ClientService;
import com.github.mtlibano.order.services.PhoneNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/phone-number")
public class PhoneNumberResource {

    @Autowired
    PhoneNumberService service;

    @Autowired
    ClientService clientService;

    @PostMapping
    public ResponseEntity<PhoneNumberDTO> insert(@RequestBody PhoneNumberDTO phoneNumberDTO) {
        return ResponseEntity.ok(service.insert(new PhoneNumber(
                phoneNumberDTO,
                clientService.findById(phoneNumberDTO.getClientId()))).
                toDTO());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PhoneNumberDTO> update(@PathVariable Integer id, @RequestBody PhoneNumberDTO phoneNumberDTO) {
        PhoneNumber phoneNumber = new PhoneNumber(phoneNumberDTO, clientService.findById(phoneNumberDTO.getClientId()));
        phoneNumber.setId(id);
        phoneNumber = service.update(phoneNumber);
        return ResponseEntity.ok(phoneNumber.toDTO());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PhoneNumberDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id).toDTO());
    }

    @GetMapping
    public ResponseEntity<List<PhoneNumberDTO>> listAll() {
        return ResponseEntity.ok(service.listAll().stream().map(PhoneNumber::toDTO).toList());
    }

    @GetMapping("/phone-number/{phoneNumber}")
    public ResponseEntity<List<PhoneNumberDTO>> findByPhoneNumber(@PathVariable String phoneNumber) {
        return ResponseEntity.ok(service.findByPhoneNumber(phoneNumber).stream().map(PhoneNumber::toDTO).toList());
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<PhoneNumberDTO>> findByClient(@PathVariable Integer clientId) {
        return ResponseEntity.ok(service.findByClient(clientService.findById(clientId)).stream().map(PhoneNumber::toDTO).toList());
    }

}