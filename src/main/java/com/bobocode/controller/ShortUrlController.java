package com.bobocode.controller;

import com.bobocode.dto.ShortenUrlRequest;
import com.bobocode.service.ShortenUrlService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;

@RestController
@RequiredArgsConstructor
public class ShortUrlController {

    public static final String HTTPS = "https://";
    public static final String SLASH = "/";

    private final ShortenUrlService shortenUrlService;

    @PostMapping("/short")
    public ResponseEntity<?> shortenUrl(@RequestBody ShortenUrlRequest shortenUrlRequest, HttpServletRequest request) {
        String shortenUrlId = shortenUrlService.shortenUrl(shortenUrlRequest);
        return ResponseEntity.created(buildShortenUrl(shortenUrlId, request)).build();
    }

    private static URI buildShortenUrl(String shortenUrlId, HttpServletRequest request) {
        String requestUrl = request.getRequestURL().toString();
        int indexOfFirstUrlSlash = StringUtils.indexOf(requestUrl, SLASH, HTTPS.length());
        String shortenUrl = requestUrl.substring(0, indexOfFirstUrlSlash + 1) + shortenUrlId;
        return URI.create(shortenUrl);
    }

    @GetMapping("/short/{shortenUrlId}")
    public void redirectShortUrl(@PathVariable String shortenUrlId, HttpServletResponse response) {
        String originalUrl = shortenUrlService.getOriginalUrl(shortenUrlId);
        performRedirect(originalUrl, response);
    }

    private static void performRedirect(String redirectUrl, HttpServletResponse response) {
        try {
            response.sendRedirect(redirectUrl);
        } catch (IOException e) {
            throw new RuntimeException("Cannot perform the redirect by URL: " + redirectUrl);
        }
    }

}
