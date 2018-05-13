package com.mikesol.github.interview.app.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {
    private static int TEST_ID = 1;
    private static String TEST_NAME = "TEST_NAME";
    private static String TEST_AUTHOR = "testAuthor";

    User testUser;

    @BeforeEach
    public void setup() {
        testUser = new User(TEST_ID, TEST_NAME, TEST_AUTHOR);
    }

    @Test
    public void getId_returnsExpectedId() {
        assertEquals(TEST_ID, testUser.getId());
    }

    @Test
    public void getName_returnsExpectedName() {
        assertEquals(TEST_NAME, testUser.getName());
    }

    @Test
    public void getAuthor_returnsExpectedId() {
        assertEquals(TEST_AUTHOR, testUser.getAuthor());
    }

    @Test
    public void setId_validId_setsTheId() {
        testUser.setId(2);
        assertEquals(2, testUser.getId());
    }

    @Test
    public void setName_validName_setsTheName() {
        testUser.setName("other name");
        assertEquals("other name", testUser.getName());
    }

    @Test
    public void setAuthor_validAuthor_setsTheAuthor() {
        testUser.setAuthor("other author");
        assertEquals("other author", testUser.getAuthor());
    }

    @Test
    public void getJsonString_validUser_returnsAValidJsonString() {
        String expectedJson = "{\"id\":1,\"name\":\"TEST_NAME\",\"author\":\"testAuthor\"}";
        assertEquals(expectedJson, testUser.getJsonString());
    }
}
