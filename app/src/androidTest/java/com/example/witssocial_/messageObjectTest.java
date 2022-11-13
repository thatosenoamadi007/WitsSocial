package com.example.witssocial_;

import junit.framework.TestCase;

public class messageObjectTest extends TestCase {

    //tests the get sender id method
    public void testGetSender_id() {
        messageObject messageObject=new messageObject("karabo@gmail.com","thato@gmail.com","Did you setup circleci?","2022/09/06","null","text");
        assertEquals("karabo@gmail.com",messageObject.getSender_id());
    }
    //tests the get reciever method
    public void testGetReceiver_id() {
        messageObject messageObject=new messageObject("karabo@gmail.com","thato@gmail.com","Did you setup circleci?","2022/09/06","null","text");
        assertEquals("thato@gmail.com",messageObject.getReceiver_id());
    }
    //tests the get content method
    public void testGetContent() {
        messageObject messageObject=new messageObject("karabo@gmail.com","thato@gmail.com","Did you setup circleci?","2022/09/06","null","text");
        assertEquals("Did you setup circleci?",messageObject.getContent());
    }
    //tests the get timestamp method
    public void testGetTimestamp() {
        messageObject messageObject=new messageObject("karabo@gmail.com","thato@gmail.com","Did you setup circleci?","2022/09/06","null","text");
        assertEquals("2022/09/06",messageObject.getTimestamp());
    }
    //tests the set sender id method
    public void testSetSender_id() {
        messageObject messageObject=new messageObject();
        messageObject.setSender_id("karabo@gmail.com");
        assertEquals("karabo@gmail.com",messageObject.getSender_id());
    }
    //tests the set receiver id method
    public void testSetReceiver_id() {
        messageObject messageObject=new messageObject();
        messageObject.setReciever_id("thato@gmail.com");
        assertEquals("thato@gmail.com",messageObject.getReceiver_id());
    }
    //tests the set content method
    public void testSetContent() {
        messageObject messageObject=new messageObject();
        messageObject.setContent("Did you setup circleci?");
        assertEquals("Did you setup circleci?",messageObject.getContent());
    }
    //tests the set timestamp method
    public void testSetTimestamp() {
        messageObject messageObject=new messageObject();
        messageObject.setTimestamp("2022/09/06");
        assertEquals("2022/09/06",messageObject.getTimestamp());
    }
}