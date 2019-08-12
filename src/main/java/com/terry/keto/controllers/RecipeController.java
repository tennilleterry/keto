package com.terry.keto.controllers;


import com.terry.keto.models.Comment;
import com.terry.keto.models.Recipe;
import com.terry.keto.models.User;
import com.terry.keto.models.data.CommentDao;
import com.terry.keto.models.data.RecipeDao;
import com.terry.keto.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("recipe")
public class RecipeController {

    @Autowired
    private RecipeDao recipeDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CommentDao commentDao;


    @RequestMapping(value = "")
    public String index(Model model, @CookieValue(value = "user", defaultValue = "none") String username) {

        if(username.equals("none")) {
            return "redirect:/user/login";
        }

        User u = userDao.findByUsername(username).get(0);
        model.addAttribute("recipes", u.getRecipes());
        //model.addAttribute("title", "My Recipes");
        model.addAttribute("user", u.getUsername());

        return "recipe/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddRecipeForm(Model model, @CookieValue(value = "user", defaultValue = "none") String username) {
        if(username.equals("none")) {
            return "redirect:/user/login";
        }
        User u = userDao.findByUsername(username).get(0);
        model.addAttribute("title", "Add recipe");
        model.addAttribute(new Recipe());


        return "recipe/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddRecipeForm(
            @ModelAttribute @Valid Recipe newRecipe,
            Errors errors,
            Model model , @CookieValue(value = "user", defaultValue = "none") String username) {
        if(username.equals("none")) {
            return "redirect:/user/login";
        }


        User u = userDao.findByUsername(username).get(0);
        if (errors.hasErrors()) {
            model.addAttribute("title", "Add recipe");
            return "recipe/add";
        }


        newRecipe.setUser(u);
        recipeDao.save(newRecipe);
        //return "redirect:";

        return "redirect:view/" + newRecipe.getId();
    }







    @RequestMapping(value="view/{id}", method = RequestMethod.GET)
    public String viewRecipe(Model model, @PathVariable int id){



        Recipe recipe = recipeDao.findById(id);

        model.addAttribute("title", recipe.getName());
        model.addAttribute("recipes", recipe.getDescription());


        model.addAttribute("recipes",recipe.getComments());
        model.addAttribute("id", recipe.getId());

        return "recipe/view";
    }



}