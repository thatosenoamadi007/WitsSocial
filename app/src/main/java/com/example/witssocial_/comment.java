package com.example.witssocial_;

public class comment {
    public String comment,id;

    public comment(){

    }
    public comment(String comment,String id){
        this.comment=comment;
        this.id=id;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}