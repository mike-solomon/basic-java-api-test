package com.mikesol.github.interview.app.client;

import okhttp3.HttpUrl;
import okhttp3.Response;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    public void postToImaginaryEndpoint_validInput_serverReceivesExpectedInput() throws Exception {
        // Given a valid server that returns a valid response
        MockWebServer server = new MockWebServer();
        server.enqueue(new MockResponse().setBody("someValidResponse"));
        server.start();

        // Given a client that points to said server
        HttpUrl baseUrl = server.url("somePath");
        client = new Client(baseUrl.toString(), "someAccessToken");

        // When the request is made with valid input
        client.postToImaginaryEndpoint("hubot deploy github/mybranch to production");

        // Then the server receives a POST request with the expected input and Content-Type
        RecordedRequest recordedRequest = server.takeRequest();
        assertEquals("POST", recordedRequest.getMethod());
        assertEquals("/somePath/deployments", recordedRequest.getPath());
        assertEquals("{\"app\":\"github\",\"environment\":\"production\",\"branch\":\"mybranch\",\"action\":\"deploy\"}", recordedRequest.getBody().readUtf8());
        assertEquals("application/json; charset=utf-8", recordedRequest.getHeader("Content-Type"));
    }

    @Test
    public void postToImaginaryEndpoint_emptyInput_expectExceptionToBeThrown() {
        // Given some invalid input
        String invalidInput = "";

        // Given a valid client
        client = new Client("someBaseUrl", "someAccessToken");

        // When postToImaginaryEndpoint is called with said input
        // Then an IllegalArgumentException is thrown
        thrown = assertThrows(IllegalArgumentException.class, () -> client.postToImaginaryEndpoint(invalidInput));

        // And a descriptive error message is returned
        assertEquals("Input must not be empty", thrown.getMessage());
    }

    @Test
    public void postToImaginaryEndpoint_notEnoughArgumentsPassedIn_expectExceptionToBeThrown() {
        // Given some invalid input
        String invalidInput = "hubot deploy";

        // Given a valid client
        client = new Client("someBaseUrl", "someAccessToken");

        // When postToImaginaryEndpoint is called with said input
        // Then an IllegalArgumentException is thrown
        thrown = assertThrows(IllegalArgumentException.class, () -> client.postToImaginaryEndpoint(invalidInput));

        // And a descriptive error message is returned
        assertEquals("We are expecting 5 pieces in input", thrown.getMessage());
    }

    @Test
    public void postToImaginaryEndpoint_firstArgumentIsNotHubot_expectExceptionToBeThrown() {
        // Given some invalid input
        String invalidInput = "deploy 1 2 3 4";

        // Given a valid client
        client = new Client("someBaseUrl", "someAccessToken");

        // When postToImaginaryEndpoint is called with said input
        // Then an IllegalArgumentException is thrown
        thrown = assertThrows(IllegalArgumentException.class, () -> client.postToImaginaryEndpoint(invalidInput));

        // And a descriptive error message is returned
        assertEquals("The first part of the input should be hubot", thrown.getMessage());
    }

    @Test
    public void postToImaginaryEndpoint_secondArgumentIsNotDeploy_expectExceptionToBeThrown() {
        // Given some invalid input
        String invalidInput = "hubot 1 2 3 4";

        // Given a valid client
        client = new Client("someBaseUrl", "someAccessToken");

        // When postToImaginaryEndpoint is called with said input
        // Then an IllegalArgumentException is thrown
        thrown = assertThrows(IllegalArgumentException.class, () -> client.postToImaginaryEndpoint(invalidInput));

        // And a descriptive error message is returned
        assertEquals("The second input received is not a valid action", thrown.getMessage());
    }

    @Test
    public void postToImaginaryEndpoint_thirdArgumentIsNotInTheFormOfAppNameSlashBranchName_expectExceptionToBeThrown() {
        // Given some invalid input
        String invalidInput = "hubot deploy 2 3 4";

        // Given a valid client
        client = new Client("someBaseUrl", "someAccessToken");

        // When postToImaginaryEndpoint is called with said input
        // Then an IllegalArgumentException is thrown
        thrown = assertThrows(IllegalArgumentException.class, () -> client.postToImaginaryEndpoint(invalidInput));

        // And a descriptive error message is returned
        assertEquals("The third parameter needs to be in the expected format of appName/branch", thrown.getMessage());
    }

    @Test
    public void postToImaginaryEndpoint_thirdArgumentContainsAnEmptyAppName_expectExceptionToBeThrown() {
        // Given some invalid input
        String invalidInput = "hubot deploy /branchName 3 4";

        // Given a valid client
        client = new Client("someBaseUrl", "someAccessToken");

        // When postToImaginaryEndpoint is called with said input
        // Then an IllegalArgumentException is thrown
        thrown = assertThrows(IllegalArgumentException.class, () -> client.postToImaginaryEndpoint(invalidInput));

        // And a descriptive error message is returned
        assertEquals("Neither the appName nor the branchName can be empty", thrown.getMessage());
    }

    @Test
    public void postToImaginaryEndpoint_fourthArgumentIsNotTo_expectExceptionToBeThrown() {
        // Given some invalid input
        String invalidInput = "hubot deploy appName/branchName 3 4";

        // Given a valid client
        client = new Client("someBaseUrl", "someAccessToken");

        // When postToImaginaryEndpoint is called with said input
        // Then an IllegalArgumentException is thrown
        thrown = assertThrows(IllegalArgumentException.class, () -> client.postToImaginaryEndpoint(invalidInput));

        // And a descriptive error message is returned
        assertEquals("The 4th input received should be the word to", thrown.getMessage());
    }

//    @Test
//    public void postToImaginaryEndpoint_validInput_postRequestMadeWithExpectedParams() throws Exception {
//        // Given a valid server that returns a valid response
//        MockWebServer server = new MockWebServer();
//        server.enqueue(new MockResponse().setBody("someValidResponse"));
//        server.start();
//
//        // Given a client that points to said server
//        HttpUrl baseUrl = server.url("somePath");
//        client = new Client(baseUrl.toString(), "someAccessToken");
//
//        // When the request is made with valid input
//        client.postToImaginaryEndpoint("someValidInput");
//
//        // Then the server receives a POST request with the expected input and Content-Type
//        RecordedRequest recordedRequest = server.takeRequest();
//        assertEquals("POST", recordedRequest.getMethod());
//        assertEquals("/somePath/foo", recordedRequest.getPath());
//        assertEquals("someValidInput", recordedRequest.getBody().readUtf8());
//        assertEquals("application/json; charset=utf-8", recordedRequest.getHeader("Content-Type"));
//    }

    @Test
    public void createGitHubIssue_invalidInput_throwsIllegalArgumentException() {
        // Given some invalid input
        String invalidInput = "";

        // Given a valid client
        client = new Client("someBaseUrl", "someAccessToken");

        // When createGitHubIssue is called with said input
        // Then an IllegalArgumentException is thrown
        thrown = assertThrows(IllegalArgumentException.class, () -> client.createGitHubIssue(invalidInput));

        // And a descriptive error message is returned
        assertEquals("createGitHubIssue method requires non-empty input", thrown.getMessage());
    }

    @Test
    public void createGitHubIssue_validInput_postRequestMadeWithExpectedParams() throws Exception {
        // Given a valid server that returns a valid response
        MockWebServer server = new MockWebServer();
        server.enqueue(new MockResponse().setBody("someValidResponse"));
        server.start();

        // Given a client that points to said server
        HttpUrl baseUrl = server.url("somePath");
        client = new Client(baseUrl.toString(), "someAccessToken");

        // When the request is made with valid input
        Response response = client.createGitHubIssue("someValidInput");

        // Then the server receives a POST request with the expected input and Content-Type
        RecordedRequest recordedRequest = server.takeRequest();
        assertEquals("POST", recordedRequest.getMethod());
        assertEquals("/somePath/repos/mikesol314/github-interview/issues?access_token=someAccessToken", recordedRequest.getPath());
        assertEquals("someValidInput", recordedRequest.getBody().readUtf8());
        assertEquals("application/json; charset=utf-8", recordedRequest.getHeader("Content-Type"));

        // Then we get the expected response back
        assertEquals("someValidResponse", response.body().string());
    }

    @Test
    public void getGitHubIssue_validInput_getRequestMadeWithExpectedParams() throws Exception {
        // Given a valid server that returns a valid response
        MockWebServer server = new MockWebServer();
        server.enqueue(new MockResponse().setBody("someValidResponse"));
        server.start();

        // Given a client that points to said server
        HttpUrl baseUrl = server.url("somePath");
        client = new Client(baseUrl.toString(), "someAccessToken");

        // When the request is made with valid input
        Response response = client.getGitHubIssues();

        // Then the server receives a GET request
        RecordedRequest recordedRequest = server.takeRequest();
        assertEquals("GET", recordedRequest.getMethod());
        assertEquals("/somePath/repos/mikesol314/github-interview/issues?access_token=someAccessToken", recordedRequest.getPath());

        // Then we get the expected response back
        assertEquals("someValidResponse", response.body().string());
    }
}
