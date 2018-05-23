package com.mikesol.github.interview.app.client;

import com.mikesol.github.interview.deployment.Deployment;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Logger;

public class Client {
    private static final Logger LOGGER = Logger.getLogger(Client.class.getName());
    private static final MediaType JSON_MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");
    private static final String CREATE_ISSUE_PATH = "/repos/mikesol314/github-interview/issues";
    private static final String GET_ISSUE_PATH = "/repos/mikesol314/github-interview/issues";
    private static final String DEPLOYMENTS_PATH = "/deployments";
    // appName ?
    // production ?

    private OkHttpClient client;
    private String baseUrl;
    private String accessToken;

    public Client(String baseUrl, String accessToken) {
        this.baseUrl = baseUrl;
        this.accessToken = accessToken;
        client = new OkHttpClient();
    }

    // "hubot deploy github to production"
    public Response postToImaginaryEndpoint(String input) throws Exception {
        LOGGER.info("Received a request with input:" + input);

        // Parsing - validation?
        // split string
        // 0 == hubot if InvalidInputException
        // 1 == deploy
        // 2 == appName/branch
        // 3 == to
        // 4 == environment
        Deployment deployment = parseAndValidateInput(input);

        HttpUrl url = HttpUrl.get(new URI(baseUrl + DEPLOYMENTS_PATH));
        RequestBody body = RequestBody.create(JSON_MEDIA_TYPE, deployment.getJsonString());

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        return client.newCall(request).execute();
    }

    private Deployment parseAndValidateInput(String input) throws IllegalArgumentException {
        if (StringUtils.isEmpty(input)) {
            throw new IllegalArgumentException("Input must not be empty");
        }

        String[] splitString = input.split(" ");
        if (splitString.length != 5) {
            throw new IllegalArgumentException("We are expecting 5 pieces in input");
        }

        if (!splitString[0].equals("hubot")) {
            throw new IllegalArgumentException("The first part of the input should be hubot");
        }

        if (!Deployment.VALID_ACTIONS.contains(splitString[1])) {
            throw new IllegalArgumentException("The second input received is not a valid action");
        }

        String action = splitString[1];

        if (!splitString[2].contains("/")) {
            throw new IllegalArgumentException("The third parameter needs to be in the expected format of appName/branch");
        }

        String appName = splitString[2].split("/")[0];
        String branchName = splitString[2].split("/")[1];

        if (StringUtils.isEmpty(appName) || StringUtils.isEmpty(branchName)) {
            throw new IllegalArgumentException("Neither the appName nor the branchName can be empty");
        }

        if (!splitString[3].equals("to")) {
            throw new IllegalArgumentException("The 4th input received should be the word to");
        }

        String environnmentName = splitString[4];

        Deployment deployment = new Deployment(appName, environnmentName, branchName, action);

        return deployment;
    }

    // TODO: Parse out the response into the expected struct
    public Response createGitHubIssue(String jsonInput) throws IOException, URISyntaxException {
        LOGGER.info("Received a request with input:" + jsonInput);

        HttpUrl url = HttpUrl.get(new URI(baseUrl + CREATE_ISSUE_PATH + "?access_token=" + accessToken));

        if (StringUtils.isEmpty(jsonInput)) {
            throw new IllegalArgumentException("createGitHubIssue method requires non-empty input");
        }

        RequestBody body = RequestBody.create(JSON_MEDIA_TYPE, jsonInput);

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        return client.newCall(request).execute();
    }

    // TODO: Parse out the response into the expected struct
    public Response getGitHubIssues() throws IOException, URISyntaxException {
        HttpUrl url = HttpUrl.get(new URI(baseUrl + GET_ISSUE_PATH + "?access_token=" + accessToken));

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        return client.newCall(request).execute();
    }
}