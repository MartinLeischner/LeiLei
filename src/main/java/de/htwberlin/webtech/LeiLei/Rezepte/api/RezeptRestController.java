package de.htwberlin.webtech.LeiLei.Rezepte.api;

import de.htwberlin.webtech.LeiLei.Rezepte.api.persistence.RezeptEntity;
import de.htwberlin.webtech.LeiLei.Rezepte.api.persistence.RezeptRepository;
import de.htwberlin.webtech.LeiLei.service.RezeptService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
