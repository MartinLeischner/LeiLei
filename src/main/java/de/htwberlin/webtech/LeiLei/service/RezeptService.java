package de.htwberlin.webtech.LeiLei.service;

import de.htwberlin.webtech.LeiLei.Rezepte.api.Rezept;
import de.htwberlin.webtech.LeiLei.Rezepte.api.RezeptCreateOrUpdateRequest;
import de.htwberlin.webtech.LeiLei.Rezepte.api.persistence.RezeptEntity;
import de.htwberlin.webtech.LeiLei.Rezepte.api.persistence.RezeptRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

    public Rezept create(RezeptCreateOrUpdateRequest request){
        var rezeptEntity = new RezeptEntity(request.getName(), request.getDifficulty(), request.getIngredient(), request.getTime());
        rezeptEntity = rezeptRepository.save(rezeptEntity);
        return transformEntity(rezeptEntity);

    }

    public Rezept update(Long id, RezeptCreateOrUpdateRequest request){
        var rezeptEntityOptional  = rezeptRepository.findById(id);
        if(rezeptEntityOptional.isEmpty()){
            return null;
        }

        var rezeptEntity = rezeptEntityOptional.get();
        rezeptEntity.setName(request.getName());
        rezeptEntity.setIngredient((request.getIngredient()));
        rezeptEntity.setDifficulty(request.getDifficulty());
        rezeptEntity.setTime(request.getTime());
        rezeptEntity = rezeptRepository.save(rezeptEntity);

        return transformEntity(rezeptEntity);
    }

    public boolean deleteById(Long id){
        if (!rezeptRepository.existsById(id)){
            return false;
        }

        rezeptRepository.deleteById(id);
        return true;
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
