package com.mikesol.github.interview;

import com.mikesol.github.interview.app.client.Client;
import com.mikesol.github.interview.app.user.User;

import java.util.logging.Logger;

public class Application {
    private static final Logger LOGGER = Logger.getLogger(Application.class.getName());

    public static void main(String... args) {
        LOGGER.info("Starting GitHub interview service...");

        User user = new User(1, "someName", "someAuthor");
        String json = user.getJsonString();

        LOGGER.info("json string for user: " + json);

        Client client = new Client();
        try {
            client.doRequest(json);
        } catch (Exception e) {
            LOGGER.warning("Got an exception while making a request to the client: " + e);
        }
    }
}
