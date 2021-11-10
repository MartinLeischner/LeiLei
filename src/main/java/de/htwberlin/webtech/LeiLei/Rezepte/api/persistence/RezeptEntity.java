package de.htwberlin.webtech.LeiLei.Rezepte.api.persistence;


import javax.persistence.*;

@Entity(name = "Rezepte")
public class RezeptEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "Rezeptname", nullable = false)
    private String name;

    @Column(name = "Zutat", nullable = false)
    private String ingredient;

    @Column(name = "Schwierigkeitsgrad", nullable = true)
    private String difficulty;

    @Column(name = "Zeit", nullable = true)
    private long time;

    public RezeptEntity(long id, String name, String ingredient, String difficulty, long time) {
        this.id = id;
        this.name = name;
        this.ingredient = ingredient;
        this.difficulty = difficulty;
        this.time = time;
    }

    protected RezeptEntity() {
    }

    public long getId() {
        return id;
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
