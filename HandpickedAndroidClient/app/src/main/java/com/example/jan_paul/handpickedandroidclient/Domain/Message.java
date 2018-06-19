package com.example.jan_paul.handpickedandroidclient.Domain;

public class Message {
    private String messageContent;
    private String timeStamp;
    private String sender;
    private Boolean seen;

    public Message(String messageContent, String timeStamp, String sender) {
        this.messageContent = messageContent;
        this.timeStamp = timeStamp;
        this.sender = sender;
        this.seen = false;
    }

    public Boolean getSeen() {
        return seen;
    }

    public void setSeen(Boolean seen) {
        this.seen = seen;
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

    @Override
    public String toString() {
        return "Message{" +
                "messageContent='" + messageContent + '\'' +
                ", timeStamp='" + timeStamp + '\'' +
                ", sender='" + sender + '\'' +
                '}';
    }
}
