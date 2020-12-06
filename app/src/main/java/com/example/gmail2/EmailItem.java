package com.example.gmail2;

import java.util.ArrayList;
import java.util.List;

public class EmailItem {
    private  int avatar = R.drawable.thumb1;
    private String sender;
    private String brief;
    private String date;
    private boolean favorite = false;
    private String email;
    private  String subject;

    public EmailItem(String email, String sender, String subject, String brief, String date, boolean favorite) {
        this.sender = sender;
        this.brief = brief;
        this.date = date;
        this.email = email;
        this.subject = subject;
        this.favorite = favorite;
    }

    public int getAvatar() {
        return avatar;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean getFavorite() {
        return this.favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public static List<EmailItem> samples(){
        // Creating a list
        List<EmailItem> emails
                = new ArrayList<EmailItem>();
        for (int i = 1; i <= 100; i++){
            Boolean favorite = false;
            if (i%5==0) favorite = true;
            emails.add(new EmailItem("admin"+i+"@gmail.com", "Người gửi "+i, "Chủ đề "+i, "Nội dung " + i, "12:30PM", favorite));
        }
        return emails;
    }
}