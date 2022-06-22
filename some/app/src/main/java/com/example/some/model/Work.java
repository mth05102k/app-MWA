package com.example.some.model;

import java.io.Serializable;

public class Work implements Serializable {
    private int id;
    private String title, content, date, status;
    private boolean isCooperated;

    public Work() {
    }

    public Work(int id, String title, String content, String date, String status, boolean isCooperated) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;
        this.status = status;
        this.isCooperated = isCooperated;
    }

    public Work(String title, String content, String date, String status, boolean isCooperated) {
        this.title = title;
        this.content = content;
        this.date = date;
        this.status = status;
        this.isCooperated = isCooperated;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isCooperated() {
        return isCooperated;
    }

    public void setCooperated(boolean cooperated) {
        isCooperated = cooperated;
    }
}
