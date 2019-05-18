package com.terry.keto.models;

import javax.persistence.Entity;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class Recipe {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min = 8, max = 100, message = "min 5 max 25")
    private String name;

    @NotNull
    @Size(min = 8, max = 300, message = "min 10 max 100")
    private String description;


    @ManyToOne
    private User user;

    public Recipe(String name, String description) {
        this.name = name;
        this.description = description;

    }

    public Recipe() { }

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

    public User getUser() {

        return user;
    }
    public void setUser(User u) {

        this.user = u;
    }

}
