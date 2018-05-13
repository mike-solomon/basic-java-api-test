package com.mikesol.github.interview.app.client;

import okhttp3.*;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Logger;

public class Client {
    private static final Logger LOGGER = Logger.getLogger(Client.class.getName());
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private HttpUrl baseUrl;
    private OkHttpClient client;

    public Client(String baseUrl) throws URISyntaxException {
        this.baseUrl = HttpUrl.get(new URI(baseUrl));
        client = new OkHttpClient();
    }

    public Response doRequest(String jsonInput) throws IOException {
        LOGGER.info("Received a request with input:" + jsonInput);

        if (StringUtils.isEmpty(jsonInput)) {
            throw new IllegalArgumentException("doRequest method requires non-empty input");
        }

        RequestBody body = RequestBody.create(JSON, jsonInput);

        Request request = new Request.Builder()
                .url(baseUrl)
                .post(body)
                .build();

        return client.newCall(request).execute();
    }
}