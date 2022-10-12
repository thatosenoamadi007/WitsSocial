package com.example.witssocial_;

import junit.framework.TestCase;

public class CommentTest extends TestCase {

    public void testSetPost() {
        likers likers=new likers();
        likers.setLikerID("like");
        assertEquals("like",likers.getLikerID());
    }

    public void testGetPost() {
        likers likers=new likers("like");
        assertEquals("like",likers.getLikerID());
    }
    public void setComment() {
        comment comment=new comment();
        comment.setComment("comment");
        assertEquals("comment",comment.getComment());
    }

    public void getComment() {
        comment comment=new comment("comment","id");
        assertEquals("comment",comment.getComment());
    }

    public void setId() {
        comment comment=new comment();
        comment.setId("id");
        assertEquals("id",comment.getComment());
    }

    public void getId() {
        comment comment=new comment("comment","id");
        assertEquals("id",comment.getId());
    }

}