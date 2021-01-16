package com.terry.keto.controllers;


import com.terry.keto.models.*;
import com.terry.keto.models.data.CommentDao;
import com.terry.keto.models.data.IngredientDao;
import com.terry.keto.models.data.RecipeDao;
import com.terry.keto.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("ingredient")
public class IngredientController {

    @Autowired
    private RecipeDao recipeDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private IngredientDao ingredientDao;


  /*  @RequestMapping(value = "")
    public String index(Model model, @CookieValue(value = "user", defaultValue = "none") String username) {

        if(username.equals("none")) {
            return "redirect:/user/login";
        }

        User u = userDao.findByUsername(username).get(0);
        model.addAttribute("recipes", u.getRecipes());
        model.addAttribute("user", u.getUsername());


        return "recipe/index";
    }

*/



    @RequestMapping(value = "add/{id}", method = RequestMethod.GET)
    public String displayAddIngredientForm(Model model, @PathVariable int id,
                                           @CookieValue(value = "user", defaultValue = "none") String username) {
        if(username.equals("none")) {
            return "redirect:/user/login";
        }
        User u = userDao.findByUsername(username).get(0);


        model.addAttribute("title", "Add Ingredient");
        Recipe recipe = recipeDao.findById(id);

        model.addAttribute("ingredients",recipe.getIngredients());

        model.addAttribute(new Ingredient());


        return "ingredient/add";
    }




    @RequestMapping(value = "add/{id}", method = RequestMethod.POST)
    public String processAddIngredientForm(
            @ModelAttribute @Valid Ingredient newIngredient,
            @PathVariable int id,
            Errors errors,
            Model model , @CookieValue(value = "user", defaultValue = "none") String username){


        if(username.equals("none")) {
            return "redirect:/user/login";
        }
        User u = userDao.findByUsername(username).get(0);


        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Ingredient");
            return "ingredient/add";
        }


        String anIngredient = newIngredient.camelCase(newIngredient.getName());
        newIngredient.setName(anIngredient);


        Recipe recipe = recipeDao.findById(id);
        newIngredient.setRecipe(recipe);
        ingredientDao.save(newIngredient);


        return "redirect:/ingredient/add/" + recipe.getId();




    }

















 /*   @RequestMapping(value = "remove", method = RequestMethod.GET)
    public String displayRemoveRecipeForm(Model model,@CookieValue(value = "user", defaultValue = "none") String username) {
        //User u = userDao.findByUsername(username).get(0);

        if(username.equals("none")) {
            return "redirect:/user/login";
        }


        User u = userDao.findByUsername(username).get(0);


        model.addAttribute("recipes", u.getRecipes());
        model.addAttribute("title", "Remove recipe");
        return "recipe/remove";
    }

    @RequestMapping(value = "remove", method = RequestMethod.POST)
    public String processRemoveRecipeForm(@RequestParam int[] ids) {

        for (int id : ids) {
            recipeDao.deleteById(id);
        }
        return "redirect:";
    }


*/

}