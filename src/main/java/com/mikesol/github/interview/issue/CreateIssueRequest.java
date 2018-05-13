package com.mikesol.github.interview.issue;

import com.google.gson.Gson;

public class CreateIssueRequest {
    private String title;
    private String body;

    public CreateIssueRequest(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getJsonString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
