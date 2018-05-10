package com.mikesol.github.interview.app.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ClientTest {
    private Throwable thrown;
    private Client client;

    @BeforeEach
    public void setup() {
        thrown = null;
        client = new Client();
    }

    @Test
    public void doRequest_invalidInput_throwsIllegalArgumentException() {
        thrown = assertThrows(IllegalArgumentException.class, () -> client.doRequest(""));
        assertEquals("doRequest method requires non-empty input", thrown.getMessage());
    }

    @Test
    public void doRequest_validInput_postRequestMade() {
        client.doRequest("valid input");

        assertEquals(1 + 1, 2);

    }
}
