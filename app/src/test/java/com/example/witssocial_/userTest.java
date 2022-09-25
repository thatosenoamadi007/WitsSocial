package com.example.witssocial_;

import junit.framework.TestCase;

public class userTest extends TestCase {

    public void testGetUsername() {
        user user=new user("karabo@gmail.com");
        assertEquals("karabo@gmail.com",user.getUsername());
    }

    public void testSetUsername() {
        user user=new user();
        user.setUsername("karabo@gmail.com");
        assertEquals("karabo@gmail.com",user.getUsername());
    }
}