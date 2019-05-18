package com.terry.keto.models;

import javax.persistence.Entity;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.List;

@Entity
public class Photo {

    @Id
    @GeneratedValue
    private int id;


    @NotNull
    @Size(min = 8, max = 100, message = "min 7")
    private String description;

    @ManyToOne
    private User user;

    public Photo(String description) {
        this.description = description;

    }



    public Photo() { }

    public int getId() {

        return id;
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