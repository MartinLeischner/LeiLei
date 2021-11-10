package de.htwberlin.webtech.LeiLei.service;

import de.htwberlin.webtech.LeiLei.Rezepte.api.Rezept;
import de.htwberlin.webtech.LeiLei.Rezepte.api.RezeptCreateRequest;
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
                .map(this::transformEntity)
                .collect(Collectors.toList());

    }

    public Rezept findById(Long id){
        var rezeptEntity = rezeptRepository.findById(id);
        return rezeptEntity.map(this::transformEntity).orElse(null);
    }

    public Rezept create(RezeptCreateRequest request){
        var rezeptEntity = new RezeptEntity(request.getName(), request.getDifficulty(), request.getIngredient(), request.getTime());
        rezeptEntity = rezeptRepository.save(rezeptEntity);
        return transformEntity(rezeptEntity);

    }

    private Rezept transformEntity(RezeptEntity rezeptEntity){
        return new Rezept(
                rezeptEntity.getId(),
                rezeptEntity.getName(),
                rezeptEntity.getIngredient(),
                rezeptEntity.getDifficulty(),
                rezeptEntity.getTime()
        );
    }
}
