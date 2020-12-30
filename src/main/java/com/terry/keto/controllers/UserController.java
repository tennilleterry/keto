package com.terry.keto.controllers;

import com.terry.keto.models.User;
import com.terry.keto.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "add")
    public String add(Model model) {

        model.addAttribute("title", "User Signup");
        User user = new User();
        model.addAttribute("user", user);
        return "user/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @ModelAttribute @Valid User user, Errors errors, String verify) {
        List<User> sameName = userDao.findByUsername(user.getUsername());
        if(!errors.hasErrors() && user.getPassword().equals(verify) && sameName.isEmpty()) {
            model.addAttribute("user", user);
            userDao.save(user);

            /////////////////////
            return "user/login";
        } else {
            model.addAttribute("user", user);
            model.addAttribute("title", "User Signup");
            if(!user.getPassword().equals(verify)) {
                model.addAttribute("message", "Passwords must match");
                user.setPassword("");
            }

            if(!sameName.isEmpty()) {
                model.addAttribute("messageuser", "Username is taken, please select another one");
            }
            return "user/add";
        }
    }

    @RequestMapping(value = "login")
    public String loginForm(Model model) {
        model.addAttribute("title", "Login");
        model.addAttribute(new User());
        return "user/login";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String add(Model model, @ModelAttribute User user, HttpServletResponse response) {
        List<User> u = userDao.findByUsername(user.getUsername());
        if(u.isEmpty()) {
            model.addAttribute("messageuser", "Invalid Username");
            model.addAttribute("title", "Login");
            return "user/login";
        }

        User loggedIn = u.get(0);
        if(loggedIn.getPassword().equals(user.getPassword())) {

            Cookie c = new Cookie("user", user.getUsername());
            c.setPath("/");
            response.addCookie(c);
            return "redirect:/home";
        } else {
            model.addAttribute("messagepass", "Invalid Password");
            model.addAttribute("title", "Login");
            return "user/login";
        }
    }

    @RequestMapping(value = "logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for (Cookie c : cookies) {
                c.setMaxAge(0);
                c.setPath("/");
                response.addCookie(c);
            }
        }
        return "/index";
    }
}
