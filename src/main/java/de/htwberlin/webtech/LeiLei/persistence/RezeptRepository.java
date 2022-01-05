package de.htwberlin.webtech.LeiLei.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RezeptRepository extends JpaRepository<RezeptEntity, Long> {

    List<RezeptEntity> findAllByName(String name);

    @Override
    Page<RezeptEntity> findAll(Pageable pageable);
    
}
