package com.babble.echo.helper;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static com.babble.echo.helper.Constants.REQUESTED_ENDPOINT_WITH_ID_DOES_NOT_EXIST;
import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {

    @Test
    void testFormat() {
        assertEquals("Requested Endpoint with ID 1234 does not exist", Utils.format(REQUESTED_ENDPOINT_WITH_ID_DOES_NOT_EXIST, "1234"));
    }

    @Test
    void testGetHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        assertEquals(headers, Utils.getHeaders(headers).toSingleValueMap());
    }
}