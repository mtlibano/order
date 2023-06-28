package com.github.mtlibano.order.resources;

import com.github.mtlibano.order.domain.City;
import com.github.mtlibano.order.domain.dto.CityDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.github.mtlibano.order.services.CityService;

import java.util.List;

@RestController
@RequestMapping(value = "/city")
public class CityResource {
	
	@Autowired
	CityService service;

	@PostMapping
	public ResponseEntity<CityDTO> insert(@RequestBody CityDTO cityDTO) {
		City newCity = service.insert(new City(cityDTO));
		return ResponseEntity.ok(newCity.toDTO());
	}

	@PutMapping("/{id}")
	public ResponseEntity<CityDTO> update(@PathVariable Integer id, @RequestBody CityDTO cityDTO) {
		City city = new City(cityDTO);
		city.setId(id);
		city = service.update(city);
		return ResponseEntity.ok(city.toDTO());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<CityDTO> findById(@PathVariable Integer id) {
		return ResponseEntity.ok(service.findById(id).toDTO());
	}

	@GetMapping
	public ResponseEntity<List<CityDTO>> listAll() {
		return ResponseEntity.ok(service.listAll().stream().map(city -> city.toDTO()).toList());
	}

	@GetMapping("/description/{description}")
	public ResponseEntity<List<CityDTO>> findByDescriptionIgnoreCase(@PathVariable String description) {
		return ResponseEntity.ok(service.findByDescriptionIgnoreCase(description).stream().map(city -> city.toDTO()).toList());
	}

	@GetMapping("/uf/{uf}")
	public ResponseEntity<List<CityDTO>> findByUfIgnoreCase(@PathVariable String uf) {
		return ResponseEntity.ok(service.findByUfIgnoreCase(uf).stream().map(city -> city.toDTO()).toList());
	}

}