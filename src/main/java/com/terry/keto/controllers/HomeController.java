package com.terry.keto.controllers;

import com.terry.keto.models.Ingredient;
import com.terry.keto.models.Photo;
import com.terry.keto.models.Recipe;
import com.terry.keto.models.User;
import com.terry.keto.models.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@Controller
@RequestMapping(value = "home")
public class HomeController {

    @Autowired
    RecipeDao recipeDao;

    @Autowired
    CommentDao commentDao;

    @Autowired
    UserDao userDao;

    @Autowired
    PhotoDao photoDao;

    @Autowired
    IngredientDao ingredientDao;




    @RequestMapping(value="")
    public String index(Model model, @CookieValue(value = "user", defaultValue = "none") String username){
        if(username.equals("none")) {
            return "redirect:/user/login";
        }

        User u = userDao.findByUsername(username).get(0);
        model.addAttribute("user", u.getUsername());
        model.addAttribute("recipes", recipeDao.findAll());
        model.addAttribute("byName", Comparator.comparing(Recipe::getName));


    /*  List<String> recipeList = new ArrayList<>();
        for (Recipe theRecipe: recipeDao.findAll()) {
            String name = theRecipe.getName();
            recipeList.add(name);

        }

        Recipe theRecipe = new Recipe();
        model.addAttribute("randomElement", theRecipe.getRandomElement(recipeList));

        */

        List<Recipe> recipeList = new ArrayList<>();
        for (Recipe theRecipe: recipeDao.findAll()) {
            //String name = theRecipe.getName();
            recipeList.add(theRecipe);

        }


      /*  Random r = new Random();

        int randomItem = r.nextInt(recipeList.size());
        Recipe randomElement = recipeList.get(randomItem);

        model.addAttribute("randomElement", randomElement);
*/

        Recipe theRecipe = new Recipe();
        model.addAttribute("randomElement", theRecipe.getRandomElement(recipeList));
        return "home/index";



    }

    @RequestMapping(value = "search", method = RequestMethod.GET)
    public String searchResults(Model model, @Param("searchTerm") String searchTerm) {
        searchTerm = searchTerm.substring(0,1).toUpperCase() + searchTerm.substring(1);
        List<Recipe> listRecipes = (List<Recipe>) recipeDao.findAll();
        List<Recipe> results = new ArrayList<>();
        for(Recipe recipe:listRecipes){
            if(recipe.getName().contains(searchTerm)){
                results.add(recipe);
            }

        }
        model.addAttribute("searchTerm", searchTerm);
        model.addAttribute("results", results);
        return "home/search";
    }


    @RequestMapping(value = "searchByIngredient", method = RequestMethod.GET)
    public String searchByIngredientsResults(Model model, @Param("searchByIngredient") String searchByIngredient) {
        searchByIngredient = searchByIngredient.substring(0,1).toUpperCase() + searchByIngredient.substring(1);
        List<Ingredient> listIngredients = (List<Ingredient>) ingredientDao.findAll();
        List<Recipe> results = new ArrayList<>();
        for(Ingredient ingredient:listIngredients){
            if(ingredient.getName().contains(searchByIngredient)){
                results.add(ingredient.getRecipe());
            }

        }
        model.addAttribute("searchByIngredient", searchByIngredient);
        model.addAttribute("results", results);
        return "home/searchByIngredient";
    }







    @RequestMapping(value="view/{id}", method = RequestMethod.GET)
    public String viewUserRecipeList(Model model, @CookieValue(value = "user", defaultValue = "none") String username,
                                     @PathVariable int id){

        if(username.equals("none")) {
            return "redirect:/user/login";
        }
        User u = userDao.findById(id);

        model.addAttribute("recipes", u.getRecipes());

        model.addAttribute("user", u.getUsername());
        model.addAttribute("pics", u.getPhotos());


        return "home/userRecipeList";
    }

    @RequestMapping(value="userPhotos/{id}")
    public String viewUserPhotoList(Model model, @PathVariable int id){


        Photo photo = photoDao.findById(id);
        model.addAttribute("thisPhoto", photo.getPhotosImagePath());

        return "home/userPhotosView";
    }








}