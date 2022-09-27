package com.example.witssocial_;

public class Followers {
    public String followers,following;
    public Followers(){}

    public Followers(String followers,String following){
        this.followers=followers;
        this.following=following;
    }
    public String getFollowers() {
        return followers;
    }

    public void setFollowers(String followers) {
        this.followers = followers;
    }

    public String getFollowing() {
        return following;
    }

    public void setFollowing(String following) {
        this.following = following;
    }
}
