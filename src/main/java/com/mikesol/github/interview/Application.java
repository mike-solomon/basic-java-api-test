package com.mikesol.github.interview;

import com.mikesol.github.interview.app.client.Client;
import com.mikesol.github.interview.app.user.User;

public class Application {
    public static void main(String... args) {
        System.out.println("Starting GitHub interview service...");

        User user = new User(1, "someName", "someAuthor");
        String json = user.getJsonString();

        System.out.println("json string for user: " + json);

        Client client = new Client();
        try {
            client.doRequest(json);
        } catch (Exception e) {
            System.out.println("Got an exception: " + e);
        }
    }
}
