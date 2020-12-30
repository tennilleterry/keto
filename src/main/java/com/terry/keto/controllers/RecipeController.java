package com.terry.keto.controllers;


import com.terry.keto.models.*;
import com.terry.keto.models.data.CommentDao;
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


    //Code with image
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddRecipeForm(
            @ModelAttribute @Valid Recipe newRecipe,
            Errors errors,
            Model model , @CookieValue(value = "user", defaultValue = "none") String username,
            @RequestParam("image") MultipartFile multipartFile) throws IOException{


        if(username.equals("none")) {
            return "redirect:/user/login";
        }


        User u = userDao.findByUsername(username).get(0);
        if (errors.hasErrors()) {
            model.addAttribute("title", "Add recipe");
            return "recipe/add";
        }

        String aName = newRecipe.camelCase(newRecipe.getName());
        newRecipe.setName(aName);

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        newRecipe.setPhoto(fileName);

        Recipe savedPhoto = recipeDao.save(newRecipe);

        String uploadDir = "user-photos/" + savedPhoto.getId();

        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

        newRecipe.setUser(u);
        recipeDao.save(newRecipe);




        return "redirect:";
    }










    @RequestMapping(value = "remove", method = RequestMethod.GET)
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




}