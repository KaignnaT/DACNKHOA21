package com.example.khoa21c3_dacn;

public class Kieumau {
    String message;
    String sendtoid;
    long time;

    public Kieumau() {
    }

    public Kieumau(String message, String sendtoid, long time) {
        this.message = message;
        this.sendtoid = sendtoid;
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSendtoid() {
        return sendtoid;
    }

    public void setSendtoid(String sendtoid) {
        this.sendtoid = sendtoid;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
