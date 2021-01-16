package com.terry.keto.controllers;

import com.terry.keto.models.Comment;
import com.terry.keto.models.Recipe;
import com.terry.keto.models.User;
import com.terry.keto.models.data.CommentDao;

import com.terry.keto.models.data.RecipeDao;
import com.terry.keto.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("comment")
public class CommentController {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private RecipeDao recipeDao;

    @Autowired
    private UserDao userDao;



    @RequestMapping(value = "")
    public String index(Model model,@CookieValue(value = "user", defaultValue = "none")
            String username) {

        if(username.equals("none")) {
            return "redirect:/user/login";
        }

        User u = userDao.findByUsername(username).get(0);

        model.addAttribute("comments", u.getComments());

        model.addAttribute("user", u.getUsername());





        return "comment/index";
    }



    @RequestMapping(value = "add/{id}", method = RequestMethod.GET)
    public String displayAddCommentForm(Model model, @CookieValue(value = "user", defaultValue = "none")
            String username,@PathVariable int id) {
        if(username.equals("none")) {
            return "redirect:/user/login";
        }
        User u = userDao.findByUsername(username).get(0);


        Recipe recipe = recipeDao.findById(id);


        model.addAttribute("title", recipe.getName());
        model.addAttribute("recipeAuthor", recipe.getUser());
        model.addAttribute("description",recipe.getDescription());
        model.addAttribute("ingredients",recipe.getIngredients());







        //model.addAttribute("photo",recipe.getPhoto());
        model.addAttribute("thisPhoto", recipe.getPhotosImagePath());






        List<Comment> comments = recipe.getComments();
        model.addAttribute("comments", comments);
       // model.addAttribute("comments", recipe.getComments());  revised 2 lines above same as this one??




        model.addAttribute("user",u.getUsername());




        model.addAttribute(new Comment());


        return "comment/add";
    }



    @RequestMapping(value = "add/{id}", method = RequestMethod.POST)
    public String processAddCommentForm(@ModelAttribute @Valid Comment newComment,
                                         Errors errors, @PathVariable int id, Model model, @CookieValue(value = "user", defaultValue = "none")
                                                    String username) {

        if(username.equals("none")) {
            return "redirect:/user/login";
        }

        User u = userDao.findByUsername(username).get(0);

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Comment");
            model.addAttribute("recipes", recipeDao.findAll());



            model.addAttribute("users", userDao.findAll());


            return "comment/add";
        }





        Recipe recipe = recipeDao.findById(id);


        newComment.setUser(u);
        newComment.setRecipe(recipe);
        commentDao.save(newComment);


        return "redirect:/comment/";



    }


    @RequestMapping(value = "remove", method = RequestMethod.GET)
    public String displayRemoveCommentForm(Model model, @CookieValue(value = "user", defaultValue = "none")
            String username) {
        if(username.equals("none")) {
            return "redirect:/user/login";
        }

        User u = userDao.findByUsername(username).get(0);
        model.addAttribute("comments", u.getComments());
        model.addAttribute("title", "Remove Comment");
        return "comment/remove";
    }

    @RequestMapping(value = "remove", method = RequestMethod.POST)
    public String processRemoveCommentForm(@RequestParam int[] ids) {

        for (int id : ids) {
            commentDao.deleteById(id);
        }
        return "redirect:";
    }

}