package com.example.witssocial_;

public class likers {

    //variable
    String LikerID;
    //constructor without arguments
    public likers(){

    }
    //Constructor with arguments
    public likers(String LikerID){
        this.LikerID=LikerID;

    }
    //getter and setter methods
    public void setLikerID(String LikerID){
        this.LikerID=LikerID;
    }

    public String getLikerID(){
        return this.LikerID;
    }
}
