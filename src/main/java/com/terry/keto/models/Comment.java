package com.terry.keto.models;



import javax.persistence.Entity;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Comment {

    @Id
    @GeneratedValue
    private int id;



    @NotNull
    @Size(min = 8, max = 100, message = "min 8 max 100")
    private String entry;

    @ManyToOne
    private User user;



    public Comment(String entry) {

        this.entry = entry;

    }





    @ManyToOne
    private Recipe recipe;

    public Comment() { }



    public int getId() {

        return id;
    }



    public String getEntry() {

        return entry;
    }

    public void setEntry(String entry) {

        this.entry = entry;

    }



    public Recipe getRecipe() {

        return recipe;
    }
    public void setRecipe(Recipe recipe) {

        this.recipe = recipe;
    }


    public User getUser() {

        return user;
    }
    public void setUser(User u) {

        this.user = u;
    }




}