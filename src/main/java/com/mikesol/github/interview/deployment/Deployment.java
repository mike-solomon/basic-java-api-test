package com.mikesol.github.interview.deployment;

import com.google.gson.Gson;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Deployment {
    public static Set<String> VALID_ACTIONS = new HashSet<>(Arrays.asList("deploy", "migrate"));
    String app;
    String environment;
    String branch;
    String action;

    public Deployment(String app, String environment, String branch, String action) {
        this.app = app;
        this.environment = environment;
        this.branch = branch;
        this.action = action;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getJsonString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
