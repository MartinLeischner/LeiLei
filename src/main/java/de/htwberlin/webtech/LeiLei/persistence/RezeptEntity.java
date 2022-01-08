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
    private String imageName;

    @Column(name = "Ingredient", nullable = false)
    private String ingredient;

    @Column(name = "Difficulty")
    private Integer difficulty;

    @Column(name = "Time")
    private Long time;

    public RezeptEntity(String name, String imageName, String ingredient, Integer difficulty, Long time) {
        this.name = name;
        this.imageName = imageName;
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

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imagePath) {
        this.imageName = imagePath;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
