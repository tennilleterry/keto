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
    public String index(Model model, @CookieValue(value = "user", defaultValue = "none") String username) {

        if(username.equals("none")) {
            return "redirect:/user/login";
        }

        User u = userDao.findByUsername(username).get(0);

        model.addAttribute("comments", u.getComments());
        model.addAttribute("title", "My Comments");

        return "comment/index";
    }






    @RequestMapping(value = "add/{id}", method = RequestMethod.GET)
    public String displayAddCommentForm(Model model, @CookieValue(value = "user", defaultValue = "none")
            String username, @PathVariable int id) {
        if(username.equals("none")) {
            return "redirect:/user/login";
        }
        User u = userDao.findByUsername(username).get(0);
        model.addAttribute("title", "Add comment");

        Recipe recipe = recipeDao.findById(id);
        model.addAttribute("title", recipe.getName());


        model.addAttribute("description",recipe.getDescription());
        model.addAttribute("id",recipe.getId());



        model.addAttribute(new Comment());


        return "comment/add";
    }







    @RequestMapping(value = "add/{id}", method = RequestMethod.POST)
    public String processAddCommentForm(
            @ModelAttribute @Valid Comment newComment,
            Errors errors,
            Model model , @CookieValue(value = "user", defaultValue = "none") String username, @PathVariable int id) {
        if(username.equals("none")) {
            return "redirect:/user/login";
        }


        User u = userDao.findByUsername(username).get(0);
        if (errors.hasErrors()) {
            model.addAttribute("title", "Add comment");
            return "comment/add";
        }

        Recipe theRecipe = recipeDao.findById(id);
        newComment.setUser(u);
        newComment.setRecipe(theRecipe);
        commentDao.save(newComment);


        return "redirect:view/" + newComment.getId();
        //return "comment/view/" + newComment.getId();

    }



    @RequestMapping(value="view/{id}", method = RequestMethod.GET)
    public String viewComment(Model model, @PathVariable int id){



        Comment comment = commentDao.findById(id);

        model.addAttribute("title", comment.getTitle());
        model.addAttribute("comments", comment.getEntry());


        model.addAttribute("recipes",comment.getRecipe());
        model.addAttribute("id", comment.getId());


        return "comment/view";
    }


}