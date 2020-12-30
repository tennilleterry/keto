package com.terry.keto.models;

import javax.persistence.Entity;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.List;




/*@Entity
public class Photo {

    @Id
    @GeneratedValue
    private int id;



    @NotNull
    @Size(min = 8, max = 100, message = "min 7")
    private String ;

    @ManyToOne
    private User user;

    public Photo(String description) {
        this.description = description;

    }



    public Photo() { }

    public int getId() {

        return id;
    }



    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {

        this.description = description;
    }

    public User getUser() {

        return user;
    }
    public void setUser(User u) {

        this.user = u;
    }



}
*/

@Entity
@Table(name = "photos")
public class Photo {

    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = true, length = 64)
    private String photo;

    @ManyToOne
    private User user;

    public Photo(String photo) {
        this.photo = photo;

    }



    public Photo() { }

    public int getId() {

        return id;
    }



    public String getPhoto() {

        return photo;
    }

    public void setPhoto(String photo) {

        this.photo = photo;
    }

    public User getUser() {

        return user;
    }
    public void setUser(User u) {

        this.user = u;
    }

  @Transient
  public String getPhotosImagePath() {
        if (photo == null || id == 0) return "no pic";

      return "/user-photos/" + id + "/" + photo;


    }


 /*   @Transient
    public String check() {
        return "works!!";

        //return "/user-photos/" + photo;

    }

*/


  /* public String getPhotosImagePath() {
       if (photo == null ) return "no pic";

       //return photo;
       return "/user-photos/" + photo;
   }
*/

}