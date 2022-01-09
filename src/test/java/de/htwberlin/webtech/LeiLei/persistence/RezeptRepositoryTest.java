package de.htwberlin.webtech.LeiLei.persistence;

import de.htwberlin.webtech.LeiLei.Rezepte.api.Rezept;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@EnableJpaRepositories(basePackageClasses = RezeptRepository.class)
@EntityScan(basePackageClasses = RezeptEntity.class)
class RezeptRepositoryTest {

    @Autowired
    RezeptRepository rezeptRepository;

    @BeforeEach
    void setUp() {
        rezeptRepository.deleteAll();
    }

    @Test
    @DisplayName("Test if the repository is empty")
    void findAll_empty_table() {
        assertThat(rezeptRepository.findAll()).isEmpty();
    }

    @Test
    @DisplayName("Test if the repository has one entry")
    void findAll() {
        // Prepare db
        RezeptEntity rezeptEntity = new RezeptEntity("Rezept 1", "bild1.jpg", "Zutaten 1", 1, 15L);
        rezeptRepository.save(rezeptEntity);

        // TEST
        List<RezeptEntity> rezeptEntities = rezeptRepository.findAll();
        assertThat(rezeptEntities).hasSize(1);
        assertThat(rezeptEntities).containsOnly(rezeptEntity);
    }

    // findbyid
    @Test
    @DisplayName("Test if the repository find a entry by id")
    void findExistingRezeptById() {
        // Prepare db
        RezeptEntity rezeptEntity = new RezeptEntity("Rezept 1", "bild1.jpg", "Zutaten 1", 1, 15L);
        rezeptRepository.save(rezeptEntity);

        // TEST
        RezeptEntity rezeptEntityFound = rezeptRepository.findById(1L).get();
        assertThat(rezeptEntityFound.getName()).isEqualTo(rezeptEntity.getName());
        assertThat(rezeptEntityFound.getDifficulty()).isEqualTo(rezeptEntity.getDifficulty());
        assertThat(rezeptEntityFound.getIngredient()).isEqualTo(rezeptEntity.getIngredient());
        assertThat(rezeptEntityFound.getTime()).isEqualTo(rezeptEntity.getTime());
    }

    @Test
    @DisplayName("Test if the repository save a new entry correctly")
    void save() {
        RezeptEntity rezeptEntity = new RezeptEntity("Rezept 1", "bild1.jpg", "Zutaten 1", 1, 15L);
        // insert 1 rezept
        rezeptRepository.save(rezeptEntity);

        List<RezeptEntity> rezeptEntities = rezeptRepository.findAll();
        assertThat(rezeptEntities).hasSize(1);
        assertThat(rezeptEntities).containsOnly(rezeptEntity);
    }

    // update
    @Test
    @DisplayName("Test if the repository update an existing entry correctly")
    void update() {
        // Prepare db
        RezeptEntity rezeptEntity = new RezeptEntity("Rezept 1", "bild1.jpg", "Zutaten 1", 1, 15L);
        rezeptRepository.save(rezeptEntity);

        // Update data and save
        rezeptEntity = rezeptRepository.findAll().get(0);
        rezeptEntity.setName("Rezept 2");
        rezeptRepository.save(rezeptEntity);

        // TEST
        List<RezeptEntity> rezeptEntities = rezeptRepository.findAll();
        assertThat(rezeptEntities).hasSize(1);
        assertThat(rezeptEntities).containsOnly(rezeptEntity);
        assertThat(rezeptEntities.get(0).getName()).isEqualTo("Rezept 2");
    }

    // delete
    @Test
    @DisplayName("Test if the repository delete an existing entry correctly")
    void delete() {
        // Prepare db
        RezeptEntity rezeptEntity = new RezeptEntity("Rezept 1", "bild1.jpg", "Zutaten 1", 1, 15L);
        rezeptRepository.save(rezeptEntity);

        // TEST
        rezeptRepository.delete(rezeptEntity);
        List<RezeptEntity> rezeptEntities = rezeptRepository.findAll();
        assertThat(rezeptEntities).isEmpty();
    }
}