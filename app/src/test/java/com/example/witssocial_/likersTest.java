package com.example.witssocial_;

import junit.framework.TestCase;

public class likersTest extends TestCase {

    public void testSetLikerID() {
        likers likers=new likers();
        likers.setLikerID("liker id");
        assertEquals("liker id",likers.getLikerID());
    }

    public void testGetLikerID() {
        likers likers=new likers("liker id");
        assertEquals("liker id",likers.getLikerID());
    }
}