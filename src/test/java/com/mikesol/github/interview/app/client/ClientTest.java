package com.mikesol.github.interview.app.client;

import okhttp3.HttpUrl;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ClientTest {
    private Throwable thrown;
    private Client client;

    @BeforeEach
    public void setup() {
        // Let's not see debug messages when running tests
        Logger.getLogger(Client.class.getName()).setLevel(Level.WARNING);
    }

    @Test
    public void createClient_invalidBaseUrl_throwsURISyntaxException() {
        // Given an invalid base url
        String invalidBaseUrl = "<>^{|}";

        // When we create a client with said base url
        // Then a URISyntaxException is thrown
        thrown = assertThrows(URISyntaxException.class, () -> new Client(invalidBaseUrl));
    }

    @Test
    public void doRequest_invalidInput_throwsIllegalArgumentException() throws Exception {
        // Given some invalid input
        String invalidInput = "";

        // Given a valid client
        client = new Client("validUrl");

        // When doRequest is called with said input
        // Then an IllegalArgumentException is thrown
        thrown = assertThrows(IllegalArgumentException.class, () -> client.doRequest(invalidInput));

        // And a descriptive error message is returned
        assertEquals("doRequest method requires non-empty input", thrown.getMessage());
    }

    @Test
    public void doRequest_validInput_postRequestMade() throws Exception {
        // Given a valid server that returns a response
        MockWebServer server = new MockWebServer();
        server.enqueue(new MockResponse().setBody("hello, world"));
        server.start();

        // Given a client that points to said server
        HttpUrl baseUrl = server.url("/v1/user");
        client = new Client(baseUrl.toString());

        // When the request is made with valid input
        Response response = client.doRequest("valid input");

        // Then we receive a 200 OK status ok
        assertEquals(200, response.code());
        assertEquals("OK", response.message());

        // Then we receive the expected body
        assertEquals("hello, world", response.body().string());
    }
}
