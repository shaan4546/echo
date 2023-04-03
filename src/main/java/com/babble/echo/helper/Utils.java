package com.babble.echo.helper;

import org.springframework.http.HttpHeaders;

import java.util.Map;

public class Utils {
    public static String format(String str, String... placeholders){
        return String.format(str, placeholders[0]);
    }

    public static HttpHeaders getHeaders(Map<String, String> headers) {
        HttpHeaders responseHeaders = new HttpHeaders();
        if(headers != null){
            for(Map.Entry<String, String> entry : headers.entrySet()){
                responseHeaders.set(entry.getKey(),
                        entry.getValue());
            }
        }
        return responseHeaders;
    }
}
