package com.mikesol.github.interview;

import com.mikesol.github.interview.app.client.Client;
import com.mikesol.github.interview.issue.CreateIssueRequest;
import okhttp3.Response;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class Application {
    private static final Logger LOGGER = Logger.getLogger(Application.class.getName());
    private static final String BASE_URL = "https://api.github.com";

    public static void main(String... args) throws Exception {
        LOGGER.info("Starting GitHub interview service...");

        Client client = new Client(BASE_URL, getAuthToken());
        Response createIssueResponse = client.createGitHubIssue(createIssueRequestJson());
        LOGGER.info("Received a response of: " + createIssueResponse.body().string());

        Response getIssuesResponse = client.getGitHubIssues();
        LOGGER.info("Received a response of: " + getIssuesResponse.body().string());
    }

    private static String getAuthToken() {
        String content = "";

        try {
            content = new String(Files.readAllBytes(Paths.get(".personal-access-token")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content;
    }

    private static String createIssueRequestJson() {
        CreateIssueRequest issue = new CreateIssueRequest("someTitle", "someBody");
        String json = issue.getJsonString();
        LOGGER.info("json string for user: " + json);
        return json;
    }
}
