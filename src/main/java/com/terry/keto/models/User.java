package com.terry.keto.models;

import javax.persistence.Entity;
import javax.persistence.*;
import org.hibernate.validator.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min = 8, max = 15, message = "min 3 max 15")
    private String username;

    @NotNull
    @Size(min = 8, max = 15, message = "min 7")
    private String password;

    @Email
    private String email;


    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Recipe> recipes;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Photo> photos;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Comment> comments;




    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public User() { }

    public int getId() {

        return id;
    }

    public String getUsername() {

        return username;
    }

    public void setUsername(String username) {

        this.username = username;
    }

    public String getPassword() {

        return password;
    }

    public void setPassword(String password) {

        this.password = password;
    }

    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {

        this.email = email;
    }

    public List<Recipe> getRecipes() {

        return recipes;
    }

    public List<Photo> getPhotos() {

        return photos;
    }

    public List<Comment> getComments() {

        return comments;
    }


}
