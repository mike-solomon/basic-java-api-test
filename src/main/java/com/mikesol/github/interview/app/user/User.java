package com.mikesol.github.interview.app.user;

import com.google.gson.Gson;

public class User {
    private int id;
    private String name;
    private String author;

    public User(int id, String name, String author) {
        this.id = id;
        this.name = name;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getJsonString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
