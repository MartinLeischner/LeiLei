package de.htwberlin.webtech.LeiLei.Rezepte.api;

import de.htwberlin.webtech.LeiLei.service.RezeptService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class RezeptRestController {

    private final RezeptService rezeptService;

    public RezeptRestController(RezeptService rezeptService) {
        this.rezeptService = rezeptService;
    }

    /**
     * Get all Rezepte
     * @return
     */
    @GetMapping(path ="/api/v1/rezepte")
    public ResponseEntity<List<Rezept>> fetchRezepte() {
        return ResponseEntity.ok(rezeptService.findAll());
    }

    /**
     *
     * @param id
     * @return
     */
    @GetMapping(path = "/api/v1/rezepte/{id}")
    public ResponseEntity<Rezept> fetchRezeptById(@PathVariable Long id){
        var rezept = rezeptService.findById(id);
        return rezept != null? ResponseEntity.ok(rezept) : ResponseEntity.notFound().build();
    }

    @PostMapping(path ="/api/v1/rezepte")
    public ResponseEntity<Rezept> createRezept(Rezept rezept,
            @RequestParam("image") MultipartFile multipartFile) throws URISyntaxException {
        try {
            var savedRezept = rezeptService.create(rezept, multipartFile);
            URI uri = new URI("/api/v1/rezepte/" + savedRezept.getId());
            return ResponseEntity.created(uri).body(savedRezept);
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping(path = "/api/v1/rezepte/{id}")
    public ResponseEntity<Rezept> updateRezept(@PathVariable Long id, @RequestBody Rezept rezept) {
        var updatedRezept = rezeptService.update(id, rezept);
        return updatedRezept != null? ResponseEntity.ok(updatedRezept) : ResponseEntity.notFound().build();
    }

    @DeleteMapping(path = "/api/v1/rezepte/{id}")
    public ResponseEntity<Void> deleteRezept(@PathVariable Long id){
        boolean succesful = rezeptService.deleteById(id);
        return succesful? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
