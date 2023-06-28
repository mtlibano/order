package com.github.mtlibano.order.resources;

import com.github.mtlibano.order.domain.Rating;
import com.github.mtlibano.order.domain.dto.RatingDTO;
import com.github.mtlibano.order.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/rating")
public class RatingResource {

    @Autowired
    RatingService service;

    @PostMapping
    public ResponseEntity<RatingDTO> insert(@RequestBody RatingDTO ratingDTO) {
        return ResponseEntity.ok(service.insert(new Rating(ratingDTO)).toDTO());
    }

    @PutMapping("/{id}")
    public ResponseEntity<RatingDTO> update(@PathVariable Integer id, @RequestBody RatingDTO ratingDTO) {
        Rating rating = new Rating(ratingDTO);
        rating.setId(id);
        rating = service.update(rating);
        return ResponseEntity.ok(rating.toDTO());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RatingDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id).toDTO());
    }

    @GetMapping
    public ResponseEntity<List<RatingDTO>> listAll() {
        return ResponseEntity.ok(service.listAll().stream().map(Rating::toDTO).toList());
    }

    @GetMapping("/grade/{grade}")
    public ResponseEntity<List<RatingDTO>> findByGrade(@PathVariable Integer grade) {
        return ResponseEntity.ok(service.findByGrade(grade).stream().map(Rating::toDTO).toList());
    }

    @GetMapping("/grade/{initialGrade}/{finalGrade}")
    public ResponseEntity<List<RatingDTO>> findByGradeBetween(@PathVariable Integer initialGrade, @PathVariable Integer finalGrade) {
        return ResponseEntity.ok(service.findByGradeBetween(initialGrade, finalGrade).stream().map(Rating::toDTO).toList());
    }

}