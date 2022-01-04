package de.htwberlin.webtech.LeiLei.Rezepte.api;

public class Rezept {

    private Long   id;
    private String name;
    private String imagePath;
    private String ingredient;
    private String difficulty;
    private Long   time;

    public Rezept(Long id, String name, String imagePath, String ingredient, String difficulty, Long time) {
        this.id = id;
        this.name = name;
        this.imagePath = imagePath;
        this.ingredient = ingredient;
        this.difficulty = difficulty;
        this.time = time;
    }

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
