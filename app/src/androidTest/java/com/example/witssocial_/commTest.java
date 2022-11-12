package com.example.witssocial_;

import junit.framework.TestCase;

public class commTest extends TestCase {
    //tests the setter comment method
    public void testSetComment() {
        comment comment=new comment();
        comment.setComment("comment");
        assertEquals("comment",comment.getComment());
    }
    //tests the getter comment method
    public void testGetComment() {
        comment comment=new comment("comment","id");
        assertEquals("comment",comment.getComment());
    }
    //tests the setter id method
    public void testsetId() {
        comment comment=new comment();
        comment.setId("id");
        assertEquals("id",comment.getComment());
    }
    //tests getter id method
    public void testgetId() {
        comment comment=new comment("comment","id");
        assertEquals("id",comment.getId());
    }
}

