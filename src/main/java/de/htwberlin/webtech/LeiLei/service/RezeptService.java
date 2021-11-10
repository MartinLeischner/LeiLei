package de.htwberlin.webtech.LeiLei.service;

import de.htwberlin.webtech.LeiLei.Rezepte.api.Rezept;
import de.htwberlin.webtech.LeiLei.Rezepte.api.persistence.RezeptEntity;
import de.htwberlin.webtech.LeiLei.Rezepte.api.persistence.RezeptRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RezeptService {

    private final RezeptRepository rezeptRepository;

    public RezeptService(RezeptRepository rezeptRepository) {
        this.rezeptRepository = rezeptRepository;
    }

    public List<Rezept> findAll(){
        List<RezeptEntity> rezepte = rezeptRepository.findAll();
        return rezepte.stream()
                .map(rezeptEntity -> new Rezept(
                        rezeptEntity.getId(),
                        rezeptEntity.getName(),
                        rezeptEntity.getDifficulty(),
                        rezeptEntity.getIngredient(),
                        rezeptEntity.getTime()
                ))
                .collect(Collectors.toList());

    }
}
