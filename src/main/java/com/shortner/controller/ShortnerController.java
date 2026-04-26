package com.shortner.controller;

import com.shortner.model.ShortnerInput;
import com.shortner.model.ShortnerOutput;
import com.shortner.service.ShortenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Objects;

@RestController
public class ShortnerController {

    private final ShortenService shortenService;

    public ShortnerController(ShortenService shortenService) {
        this.shortenService = shortenService;
    }

    @PostMapping("/shorten")
    public ShortnerOutput shortenURL(@RequestBody ShortnerInput input) {

        String shortUrl = shortenService.shortenURL(input);

        ShortnerOutput output = new ShortnerOutput();
        output.setShortURL("localhost:8080/api/redirect/" + shortUrl);

        return output;
    }

    @GetMapping("/redirect/{shortURL}")
    public ResponseEntity<Void> redirectShortUrl(@PathVariable String shortURL) {
        String longURL = shortenService.getLongURL(shortURL);
        if (Objects.isNull(longURL)) {
            throw new RuntimeException("URL Not Found");
        }
        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
                .location(URI.create(longURL))
                .build();
    }

}
