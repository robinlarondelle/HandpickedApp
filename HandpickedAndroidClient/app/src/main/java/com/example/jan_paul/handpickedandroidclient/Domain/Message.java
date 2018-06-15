package com.example.jan_paul.handpickedandroidclient.Domain;

public class Message {
    private String messageContent;
    private String timeStamp;
    private String sender;

    public Message(String messageContent, String timeStamp, String sender) {
        this.messageContent = messageContent;
        this.timeStamp = timeStamp;
        this.sender = sender;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
