package com.example.witssocial_;

import junit.framework.TestCase;

public class likersTest extends TestCase {
    //test if the setter method is working
    public void testSetPost() {
        likers likers=new likers();
        likers.setLikerID("like");
        assertEquals("like",likers.getLikerID());
    }
    //test if the getter method is working
    public void testGetPost() {
        likers likers=new likers("like");
        assertEquals("like",likers.getLikerID());
    }
}
