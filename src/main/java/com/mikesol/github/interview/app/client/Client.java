package com.mikesol.github.interview.app.client;

import org.apache.commons.lang3.StringUtils;

import java.util.logging.Logger;

public class Client {
    private static final Logger LOGGER = Logger.getLogger(Client.class.getName());

    public Client() {
    }

    public void doRequest(String input) {
        LOGGER.info("Received a request with input:" + input);

        if (StringUtils.isEmpty(input)) {
            throw new IllegalArgumentException("doRequest method requires non-empty input");
        }


    }
}