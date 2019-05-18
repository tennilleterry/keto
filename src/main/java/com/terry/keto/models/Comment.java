package com.terry.keto.models;

import javax.persistence.Entity;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class Comment {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min = 8, max = 100, message = "min 3 max 15")
    private String title;

    @NotNull
    @Size(min = 8, max = 100, message = "min 7")
    private String entry;

    @ManyToOne
    private User user;

    public Comment(String title, String entry) {
        this.title = title;
        this.entry = entry;

    }

    public Comment() { }

    public int getId() {

        return id;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public String getEntry() {

        return entry;
    }

    public void setEntry(String entry) {

        this.entry = entry;
    }

    public User getUser() {

        return user;
    }
    public void setUser(User u) {

        this.user = u;
    }

}