package com.github.mtlibano.order.resources;

import com.github.mtlibano.order.domain.Client;
import com.github.mtlibano.order.domain.dto.ClientDTO;
import com.github.mtlibano.order.services.CityService;
import com.github.mtlibano.order.services.ClientService;
import com.github.mtlibano.order.utils.DateUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/client")
public class ClientResource {

    @Autowired
    ClientService service;

    @Autowired
    CityService cityService;

    @PostMapping
    public ResponseEntity<ClientDTO> insert(@RequestBody ClientDTO clientDTO) {
        return ResponseEntity.ok(service.insert(new Client(
                        clientDTO,
                        cityService.findById(clientDTO.getCityId()))).toDTO());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDTO> update(@PathVariable Integer id, @RequestBody ClientDTO clientDTO) {
        Client client = new Client(clientDTO, cityService.findById(clientDTO.getCityId()));
        client.setId(id);
        client = service.update(client);
        return ResponseEntity.ok(client.toDTO());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id).toDTO());
    }

    @GetMapping
    public ResponseEntity<List<ClientDTO>> listAll() {
        return ResponseEntity.ok(service.listAll().stream().map(Client::toDTO).toList());
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<ClientDTO>> findByNameIgnoreCase(@PathVariable String name) {
        return ResponseEntity.ok(service.findByNameIgnoreCase(name).stream().map(Client::toDTO).toList());
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<List<ClientDTO>> findByCpf(@PathVariable String cpf) {
        return ResponseEntity.ok(service.findByCpf(cpf).stream().map(Client::toDTO).toList());
    }
    
    @GetMapping("/date/{date}")
    public ResponseEntity<List<ClientDTO>> findByBirthDate(@RequestParam String date) {
        return ResponseEntity.ok(service.findByBirthDate(DateUtils.strToZonedDateTime(date)).stream().map(Client::toDTO).toList());
    }

    @GetMapping("/date/{initialDate}/{finalDate}")
    public ResponseEntity<List<ClientDTO>> findByBirthDateBetween(@RequestParam String initialDate, @RequestParam String finalDate) {
        return ResponseEntity.ok(service.findByBirthDateBetween(DateUtils.strToZonedDateTime(initialDate), DateUtils.strToZonedDateTime(finalDate)).stream().map(Client::toDTO).toList());
    }

}