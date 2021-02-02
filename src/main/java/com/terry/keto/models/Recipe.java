package com.terry.keto.models;

import javax.persistence.Entity;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Entity
public class Recipe {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min = 2, max = 100, message = "min 8, max 100")
    private String name;

    @NotNull
    @Size(min = 8, max = 300, message = "min 8, max 300")
    private String description;

    private LocalDate date = LocalDate.now();

    @Column(nullable = true, length = 64)
    private String photo;


    @ManyToOne
    private User user;

    @OneToMany
    @JoinColumn(name = "recipe_id")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "recipe_id")
    private List<Ingredient> ingredients = new ArrayList<>();


    public Recipe(String name, String description, String photo) {
        this.name = name;
        this.description = description;
        this.photo = photo;

    }


    public Recipe() {

    }

    public LocalDate getDate() {

        return date;
    }


    public int getId() {

        return id;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {

        this.description = description;
    }


    public String getPhoto() {

        return photo;
    }

    public void setPhoto(String photo) {

        this.photo = photo;
    }

    @Transient
    public String getPhotosImagePath() {
        if (photo == null || id == 0) return "no pic";

        return "/user-photos/" + id + "/" + photo;


    }


    public User getUser() {

        return user;
    }

    public void setUser(User u) {

        this.user = u;
    }

    public List<Comment> getComments() {

        return comments;
    }

    public List<Ingredient> getIngredients() {

        return ingredients;
    }


    public String camelCase(String name) {
        String res = "";
        res += Character.toUpperCase(name.charAt(0));
        for (int i = 1; i < name.length(); i++) {
            if (name.charAt(i - 1) == ' ') {
                res += Character.toUpperCase(name.charAt(i));
            } else {
                res += name.charAt(i);
            }
        }
        return res;
    }

 /*   public String getRandomElement(List<String> list) {
        Random r = new Random();

        int randomItem = r.nextInt(list.size());
        String randomElement = list.get(randomItem);


        return randomElement;


    }
    */

    public Recipe getRandomElement(List<Recipe> list) {
        Random r = new Random();

        int randomItem = r.nextInt(list.size());
        Recipe randomElement = list.get(randomItem);


        return randomElement;


    }


}




