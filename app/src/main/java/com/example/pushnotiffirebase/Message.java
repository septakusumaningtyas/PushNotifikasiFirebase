package com.example.pushnotiffirebase;

public class Message {
    private String title;
    private String notifytext;
    private String notifysubtext;

    public Message() {
    }

    public Message(String title, String notifytext, String notifysubtext) {
        this.title = title;
        this.notifytext = notifytext;
        this.notifysubtext = notifysubtext;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNotifytext() {
        return notifytext;
    }

    public void setNotifytext(String notifytext) {
        this.notifytext = notifytext;
    }

    public String getNotifysubtext() {
        return notifysubtext;
    }

    public void setNotifysubtext(String notifysubtext) {
        this.notifysubtext = notifysubtext;
    }
}
