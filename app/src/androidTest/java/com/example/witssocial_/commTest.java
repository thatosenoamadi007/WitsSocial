package com.example.witssocial_;

import junit.framework.TestCase;

public class commTest extends TestCase {

 //This is a comment  
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
