package com.terry.keto.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;



@Entity
public class Ingredient {
    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min = 2, max = 100, message = "min 8, max 100")
    private String name;

    @NotNull
    @Size(min = 1, max = 10, message = "min 1, max 10")
    private String unit;


    @ManyToOne
    private Recipe recipe;



    public Ingredient(String name, String unit) {
        this.name = name;
        this.unit = unit;

    }




    public Ingredient() { }

    public int getId() {

        return id;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getUnit() {

        return unit;
    }

    public void setUnit(String unit) {

        this.unit = unit;
    }




    public Recipe getRecipe() {

        return recipe;
    }
    public void setRecipe(Recipe recipe) {

        this.recipe = recipe;
    }





    public String camelCase(String name){
        String res = "";
        res += Character.toUpperCase(name.charAt(0));
        for(int i = 1; i < name.length(); i++) {
            if (name.charAt(i -1) == ' ') {
                res += Character.toUpperCase(name.charAt(i));
            } else{
                res += name.charAt(i);
            }
        }
        return res;
    }


}

