package com.mikesol.github.interview.app.client;

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

    private OkHttpClient client;
    private String baseUrl;
    private String accessToken;

    public Client(String baseUrl, String accessToken) {
        this.baseUrl = baseUrl;
        this.accessToken = accessToken;
        client = new OkHttpClient();
    }

    public void postToImaginaryEndpoint(String input) throws IOException, URISyntaxException {
        LOGGER.info("Received a request with input:" + input);

        HttpUrl url = HttpUrl.get(new URI(baseUrl + "/foo"));
        RequestBody body = RequestBody.create(JSON_MEDIA_TYPE, input);

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        client.newCall(request).execute();
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