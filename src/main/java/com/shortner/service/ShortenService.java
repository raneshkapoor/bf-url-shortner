package com.shortner.service;

import cn.hutool.core.lang.Snowflake;
import com.shortner.model.ShortnerInput;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ShortenService {

    private static final Map<String, String> SHORT_URLS = new HashMap<>();

    private static final Snowflake SNOWFLAKE = new Snowflake(1, 1);

    private static final String BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public String shortenURL(ShortnerInput input) {

        long id = SNOWFLAKE.nextId();

        String shortURL = getBase62(id);

        SHORT_URLS.put(shortURL, input.getLongURL());

        return shortURL;
    }

    private String getBase62(long id) {
        StringBuilder encodedString = new StringBuilder();
        while (id > 0) {
            int remainder = (int) (id % 62);
            encodedString.append(BASE62.charAt(remainder));
            id /= 62;
        }
        return encodedString.reverse().toString();
    }

    public String getLongURL(String shortURL) {
        return SHORT_URLS.get(shortURL);
    }

}
