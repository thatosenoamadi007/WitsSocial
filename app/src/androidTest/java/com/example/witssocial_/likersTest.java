package com.example.witssocial_;

import junit.framework.TestCase;

public class likersTest extends TestCase {

    public void testSetPost() {//Here we test if the liking feature is indeed working properly and liking the image or text
        likers likers=new likers();
        likers.setLikerID("like");
        assertEquals("like",likers.getLikerID());
    }

    public void testGetPost() {
        likers likers=new likers("like");
        assertEquals("like",likers.getLikerID());
    }
}
