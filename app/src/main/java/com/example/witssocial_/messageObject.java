package com.example.witssocial_;

public class messageObject {
    String sender_id,receiver_id,content,timestamp;
    public messageObject(){}

    public messageObject(String sender_id, String receiver_id, String content, String timestamp){
        this.sender_id=sender_id;
        this.receiver_id=receiver_id;
        this.content=content;
        this.timestamp=timestamp;
    }

    public String getSender_id() {
        return sender_id;
    }

    public String getReceiver_id() {
        return receiver_id;
    }

    public String getContent() {
        return content;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }

    public void setReciever_id(String receiver_id) {
        this.receiver_id = receiver_id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

}
