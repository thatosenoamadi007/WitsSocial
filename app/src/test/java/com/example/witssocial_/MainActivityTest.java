package com.example.witssocial_;

import junit.framework.TestCase;

public class MainActivityTest extends TestCase {

    public void testCheckNoErrors() {
        assertEquals("true",MainActivity.checkErrors("karabo sepuru","karabo@gmail.com","123456","123456"));
    }
    public void testCheckFullName(){

    }
    public void testCheckEmail(){

    }
    public void testCheckPassword(){

    }
    public void testCheckConfirmPassword(){

    }
}