package com.example.witssocial_;

public class Post {
    private String post;
    private String caption;
    private String username;
    private String id;

    public Post(){

    }

    public Post(String post,String caption, String username, String id){
        this.post=post;
        this.caption=caption;
        this.username=username;
        this.id=id;
    }


    public void setPost(String post){
        this.post=post;
    }
    public String getPost(){
        return post;
    }

    public void setCaption(String caption){
        this.caption=caption;
    }

    public String getCaption(){
        return this.caption;
    }

    public void setUsername(String username){
        this.username=username;
    }

    public String getUsername(){
        return this.username;
    }

    public void setId(String id) {this.id = id;}

    public String getId() {return id;}
}
