package de.htwberlin.webtech.LeiLei.Rezepte.api;

public class Rezept {

    private Long   id;
    private String name;
    private String imageName;
    private String ingredient;
    private Integer difficulty;
    private Long   time;

    public Rezept(Long id, String name, String imageName, String ingredient, Integer difficulty, Long time) {
        this.id = id;
        this.name = name;
        this.imageName = imageName;
        this.ingredient = ingredient;
        this.difficulty = difficulty;
        this.time = time;
    }

    public Rezept() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setImageName(String imageName) {
        this.imageName = imageName;
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
