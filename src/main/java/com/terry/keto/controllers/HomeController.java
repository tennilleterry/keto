package com.terry.keto.controllers;

import com.terry.keto.models.Recipe;
import com.terry.keto.models.data.CommentDao;
import com.terry.keto.models.data.RecipeDao;
import com.terry.keto.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping(value = "home")
public class HomeController {

    @Autowired
    RecipeDao recipeDao;
    CommentDao commentDao;
    UserDao userDao;


    @RequestMapping(value="")
    public String index(Model model){
        model.addAttribute("recipes", recipeDao.findAll());
        //model.addAttribute("comments", commentDao.findAll());
        //model.addAttribute("users", userDao.findAll());

        model.addAttribute("title", "Hello, Welcome to ketOpia !!!");

        return "home/index";
    }

    @RequestMapping(value="view/{id}", method = RequestMethod.GET)
    public String viewRecipe(Model model, @PathVariable int id){


        Recipe recipe = recipeDao.findById(id);
        model.addAttribute("title", recipe.getName());


        model.addAttribute("description",recipe.getDescription());
        model.addAttribute("id",recipe.getId());

        return "home/view";
    }




}