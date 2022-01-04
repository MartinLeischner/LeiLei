package de.htwberlin.webtech.LeiLei.persistence;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity(name = "Rezepte")
public class RezeptEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id")
    private Long id;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "Image")
    private String imagePath;

    @Column(name = "Ingredient", nullable = false)
    private String ingredient;

    @Column(name = "Difficulty")
    private String difficulty;

    @Column(name = "Time")
    private Long time;

    public RezeptEntity(String name, String imagePath, String ingredient, String difficulty, Long time) {
        this.name = name;
        this.imagePath = imagePath;
        this.ingredient = ingredient;
        this.difficulty = difficulty;
        this.time = time;
    }

    protected RezeptEntity() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
