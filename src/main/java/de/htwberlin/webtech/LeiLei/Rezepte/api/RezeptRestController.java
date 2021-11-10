package de.htwberlin.webtech.LeiLei.Rezepte.api;

import de.htwberlin.webtech.LeiLei.Rezepte.api.persistence.RezeptEntity;
import de.htwberlin.webtech.LeiLei.Rezepte.api.persistence.RezeptRepository;
import de.htwberlin.webtech.LeiLei.service.RezeptService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class RezeptRestController {


    private final RezeptService rezeptService;

    public RezeptRestController(RezeptService rezeptService) {
        this.rezeptService = rezeptService;

    }

    @GetMapping(path ="/api/v1/rezepte")
    public ResponseEntity<List<Rezept>> fetchRezepte(){
        return ResponseEntity.ok(rezeptService.findAll());
    }

    @GetMapping(path = "/api/v1/rezepte/{id}")
    public ResponseEntity<Rezept> fetchRezeptById(@PathVariable Long id){
        var rezept = rezeptService.findById(id);
        return rezept != null? ResponseEntity.ok(rezept) : ResponseEntity.notFound().build();
    }

    @PostMapping(path ="/api/v1/rezepte")
    public ResponseEntity<Void> createRezept(@RequestBody RezeptCreateRequest request) throws URISyntaxException {
        var rezept = rezeptService.create(request);
        URI uri = new URI("/api/v1/rezepte/" + rezept.getId());
        return ResponseEntity.created(uri).build();
    }
}
