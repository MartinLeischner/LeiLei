package de.htwberlin.webtech.LeiLei.Rezepte.api;

public class RezeptCreateOrUpdateRequest {

    private String name;
    private String ingredient;
    private String difficulty;
    private Long time;

    public RezeptCreateOrUpdateRequest(String name, String ingredient, String difficulty, Long time) {
        this.name = name;
        this.ingredient = ingredient;
        this.difficulty = difficulty;
        this.time = time;
    }

    public RezeptCreateOrUpdateRequest () {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
