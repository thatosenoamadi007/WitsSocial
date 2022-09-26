package com.example.witssocial_;

import junit.framework.TestCase;

public class PostTest extends TestCase {

    public void testSetPost() {
        Post post=new Post();
        post.setPost("https://firebasestorage.googleapis.com/witssocial/2022-09-06/karano.png");
        assertEquals("https://firebasestorage.googleapis.com/witssocial/2022-09-06/karano.png",post.getPost());
    }

    public void testGetPost() {
        Post post=new Post("https://firebasestorage.googleapis.com/witssocial/2022-09-06/karano.png","Top of the morning","karabo@gmail.com");
        assertEquals("https://firebasestorage.googleapis.com/witssocial/2022-09-06/karano.png",post.getPost());
    }

    public void testSetCaption() {
        Post post=new Post();
        post.setCaption("Top of the morning");
        assertEquals("Top of the morning",post.getCaption());
    }

    public void testGetCaption() {
        Post post=new Post("https://firebasestorage.googleapis.com/witssocial/2022/09/06","Top of the morning","karabo@gmail.com");
        assertEquals("Top of the morning",post.getCaption());
    }

    public void testSetUsername() {
        Post post=new Post();
        post.setUsername("karabo@gmail.com");
        assertEquals("karabo@gmail.com",post.getUsername());
    }

    public void testGetUsername() {
        Post post=new Post("https://firebasestorage.googleapis.com/witssocial/2022/09/06","Top of the morning","karabo@gmail.com");
        assertEquals("karabo@gmail.com",post.getUsername());
    }
}