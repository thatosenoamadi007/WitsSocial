package com.example.witssocial_;

public class user_class {
    String username,email,description,image;


    public user_class(){

    }


    public user_class(String email,String username,String description,String image){
        this.username = username;
        this.email=email;
        this.description=description;
        this.image=image;

    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String email){
        username=email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }
}
