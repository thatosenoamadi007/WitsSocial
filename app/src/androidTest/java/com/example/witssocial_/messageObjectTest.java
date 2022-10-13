package com.example.witssocial_;

import junit.framework.TestCase;

public class messageObjectTest extends TestCase {

    public void testGetSender_id() {
        messageObject messageObject=new messageObject("karabo@gmail.com","thato@gmail.com","Did you setup circleci?","2022/09/06");
        assertEquals("karabo@gmail.com",messageObject.getSender_id());
    }

    public void testGetReceiver_id() {
        messageObject messageObject=new messageObject("karabo@gmail.com","thato@gmail.com","Did you setup circleci?","2022/09/06");
        assertEquals("thato@gmail.com",messageObject.getReceiver_id());
    }

    public void testGetContent() {
        messageObject messageObject=new messageObject("karabo@gmail.com","thato@gmail.com","Did you setup circleci?","2022/09/06");
        assertEquals("Did you setup circleci?",messageObject.getContent());
    }

    public void testGetTimestamp() {
        messageObject messageObject=new messageObject("karabo@gmail.com","thato@gmail.com","Did you setup circleci?","2022/09/06");
        assertEquals("2022/09/06",messageObject.getTimestamp());
    }

    public void testSetSender_id() {
        messageObject messageObject=new messageObject();
        messageObject.setSender_id("karabo@gmail.com");
        assertEquals("karabo@gmail.com",messageObject.getSender_id());
    }

    public void testSetReceiver_id() {
        messageObject messageObject=new messageObject();
        messageObject.setReciever_id("thato@gmail.com");
        assertEquals("thato@gmail.com",messageObject.getReceiver_id());
    }

    public void testSetContent() {
        messageObject messageObject=new messageObject();
        messageObject.setContent("Did you setup circleci?");
        assertEquals("Did you setup circleci?",messageObject.getContent());
    }

    public void testSetTimestamp() {
        messageObject messageObject=new messageObject();
        messageObject.setTimestamp("2022/09/06");
        assertEquals("2022/09/06",messageObject.getTimestamp());
    }
}