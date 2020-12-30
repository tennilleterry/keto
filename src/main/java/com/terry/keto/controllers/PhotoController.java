package com.terry.keto.controllers;


import com.terry.keto.models.*;
import com.terry.keto.models.data.CommentDao;
import com.terry.keto.models.data.PhotoDao;
import com.terry.keto.models.data.RecipeDao;
import com.terry.keto.models.data.UserDao;
//import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
//import org.apache.commons.lang3.StringUtils;
import org.springframework.util.StringUtils;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.*;


    @Controller
    public class PhotoController {

        @Autowired
        private PhotoDao photoDao;

        @Autowired
        private UserDao userDao;


        //Works but don't need
/*
    @RequestMapping(value = "photos")
    public String view(Model model,@CookieValue(value = "user", defaultValue = "none") String username) {

        if(username.equals("none")) {
            return "redirect:/user/login";
        }
        User u = userDao.findByUsername(username).get(0);
        model.addAttribute("pics", u.getPhotos());


        return "photo/index";
    }

*/
    @RequestMapping(value = "photos/add")
   //@RequestMapping(value = "photos")
    public String index(Model model,@CookieValue(value = "user", defaultValue = "none") String username) {
       if(username.equals("none")) {
           return "redirect:/user/login";
       }
       User u = userDao.findByUsername(username).get(0);




        model.addAttribute("pics", u.getPhotos());



        return "photo/add";


    }



       @PostMapping("/photos/save")
        public RedirectView savePhoto(Photo photo,@CookieValue(value = "user", defaultValue = "none") String username,
                                     @RequestParam("image") MultipartFile multipartFile) throws IOException {

           if(username.equals("none")) {
               return new RedirectView("/user/login/");

           }

           User u = userDao.findByUsername(username).get(0);


            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            photo.setPhoto(fileName);
            photo.setUser(u);

            Photo savedPhoto = photoDao.save(photo);

            String uploadDir = "user-photos/" + savedPhoto.getId();

            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

            //return new RedirectView("/view/" + savedPhoto.getId(), true);
           return new RedirectView("/photos/add",true);


        }



        //Works when showing photo by id

/*    @RequestMapping(value = "photos/view/{id}")
    public String view(Model model, @PathVariable int id) {

        Photo newPhoto =  photoDao.findById(id);


        model.addAttribute("thisPhoto", newPhoto.getPhotosImagePath());


        //model.addAttribute("thisPhoto", newPhoto);

        return "home/view";
    }


*/




    }
