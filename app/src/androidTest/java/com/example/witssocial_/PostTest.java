package com.example.witssocial_;

import junit.framework.TestCase;

public class PostTest extends TestCase {

    //tests the set post method
    public void testSetPost() {
        Post post=new Post();
        post.setPost("https://firebasestorage.googleapis.com/witssocial/2022-09-06/karano.png");
        assertEquals("https://firebasestorage.googleapis.com/witssocial/2022-09-06/karano.png",post.getPost());
    }
    //tests the get post method
    public void testGetPost() {
        Post post=new Post("https://firebasestorage.googleapis.com/witssocial/2022-09-06/karano.png","Top of the morning","karabo@gmail.com","my id","null");
        assertEquals("https://firebasestorage.googleapis.com/witssocial/2022-09-06/karano.png",post.getPost());
    }
    //tests the set caption method
    public void testSetCaption() {
        Post post=new Post();
        post.setCaption("Top of the morning");
        assertEquals("Top of the morning",post.getCaption());
    }
    //tests the get caption method
    public void testGetCaption() {
        Post post=new Post("https://firebasestorage.googleapis.com/witssocial/2022/09/06","Top of the morning","karabo@gmail.com","my id","null");
        assertEquals("Top of the morning",post.getCaption());
    }
    //tests the set username method
    public void testSetUsername() {
        Post post=new Post();
        post.setUsername("karabo@gmail.com");
        assertEquals("karabo@gmail.com",post.getUsername());
    }
    //tests the get username method
    public void testGetUsername() {
        Post post=new Post("https://firebasestorage.googleapis.com/witssocial/2022/09/06","Top of the morning","karabo@gmail.com","my id","null");
        assertEquals("karabo@gmail.com",post.getUsername());
    }
    //tests the set post id method
    public void testSetId() {
        Post post=new Post();
        post.setId("my id");
        assertEquals("my id",post.getId());
    }
    //tests the get post id method
    public void testGetId() {
        Post post=new Post("https://firebasestorage.googleapis.com/witssocial/2022/09/06","Top of the morning","karabo@gmail.com","my id","null");
        assertEquals("my id",post.getId());
    }
}