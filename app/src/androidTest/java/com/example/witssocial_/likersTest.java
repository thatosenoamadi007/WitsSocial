package com.example.witssocial_;

import junit.framework.TestCase;

public class likersTest extends TestCase {

    public void testSetPost() {
        likers likers=new likers();
        likers.setLikerID("like");
        assertEquals("like",likers.getLikerID());
    }

    public void testGetPost() {
        likers likers=new likers("like");
        assertEquals("like",likers.getLikerID());
    }
}
