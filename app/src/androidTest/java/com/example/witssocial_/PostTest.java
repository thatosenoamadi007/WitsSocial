package com.example.witssocial_;

import junit.framework.TestCase;

public class PostTest extends TestCase {

    //check if the setter post method is working
    public void testSetPost() {
        Post post=new Post();
        post.setPost("https://firebasestorage.googleapis.com/witssocial/2022-09-06/karano.png");
        assertEquals("https://firebasestorage.googleapis.com/witssocial/2022-09-06/karano.png",post.getPost());
    }
    //check if the getter post methods is working
    public void testGetPost() {
        Post post=new Post("https://firebasestorage.googleapis.com/witssocial/2022-09-06/karano.png","Top of the morning","karabo@gmail.com","my id","null");
        assertEquals("https://firebasestorage.googleapis.com/witssocial/2022-09-06/karano.png",post.getPost());
    }
    //check if the setter post method is working
    public void testSetCaption() {
        Post post=new Post();
        post.setCaption("Top of the morning");
        assertEquals("Top of the morning",post.getCaption());
    }
    //check if the getter caption methods is working
    public void testGetCaption() {
        Post post=new Post("https://firebasestorage.googleapis.com/witssocial/2022/09/06","Top of the morning","karabo@gmail.com","my id","null");
        assertEquals("Top of the morning",post.getCaption());
    }
    //check if the setter username method is working
    public void testSetUsername() {
        Post post=new Post();
        post.setUsername("karabo@gmail.com");
        assertEquals("karabo@gmail.com",post.getUsername());
    }
    //check if the getter caption methods is working
    public void testGetUsername() {
        Post post=new Post("https://firebasestorage.googleapis.com/witssocial/2022/09/06","Top of the morning","karabo@gmail.com","my id","null");
        assertEquals("karabo@gmail.com",post.getUsername());
    }
    //check if the setter username method is working
    public void testSetId() {
        Post post=new Post();
        post.setId("my id");
        assertEquals("my id",post.getId());
    }
    //check if the getter id methods is working
    public void testGetId() {
        Post post=new Post("https://firebasestorage.googleapis.com/witssocial/2022/09/06","Top of the morning","karabo@gmail.com","my id","null");
        assertEquals("my id",post.getId());
    }
}