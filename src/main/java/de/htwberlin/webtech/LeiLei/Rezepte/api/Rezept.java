package de.htwberlin.webtech.LeiLei.Rezepte.api;

public class Rezept {

    private long id;
    private String name;
    private String ingredient;
    private String difficulty;
    private long time;

    public Rezept(long id, String name, String ingredient, String difficulty, long time) {
        this.id = id;
        this.name = name;
        this.ingredient = ingredient;
        this.difficulty = difficulty;
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
