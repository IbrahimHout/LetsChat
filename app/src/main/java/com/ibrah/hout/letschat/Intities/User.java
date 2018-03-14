package com.ibrah.hout.letschat.Intities;

/**
 * Created by trest on 12/23/2017.
 */

public class User {

    String email;
    String image;
    String name;
    String status;
    String thump_image;

    public User() {
    }

    public User(String email, String image, String name, String status, String thump_image) {
        this.email = email;
        this.image = image;
        this.name = name;
        this.status = status;
        this.thump_image = thump_image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getThump_image() {
        return thump_image;
    }

    public void setThump_image(String thump_image) {
        this.thump_image = thump_image;
    }
}
