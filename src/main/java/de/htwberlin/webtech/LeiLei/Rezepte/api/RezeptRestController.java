package de.htwberlin.webtech.LeiLei.Rezepte.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RezeptRestController {

    private List<Rezept> rezepte;

    public RezeptRestController(){
        this.rezepte = new ArrayList<>();
        rezepte.add(new Rezept(1, "Rigatoni mit Rinderhackfleisch", "Rigatoni, Rinderhackfleisch, Tomatenpesto, Kochsahne", "Mittel", 30));
        rezepte.add(new Rezept(2, "Schinkensandwich", "Brot, Schinken, Kaese, Salat", "Leicht", 10));
    }

    @GetMapping(path ="/api/v1/rezepte")
    public ResponseEntity<List<Rezept>> fetchRezepte(){
        return ResponseEntity.ok(rezepte);
    }

}
