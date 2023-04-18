package com.bobocode.service;

import com.bobocode.dto.ShortenUrlRequest;

public interface ShortenUrlService {

    String shortenUrl(ShortenUrlRequest shortenUrlRequest);

    String getOriginalUrl(String shortenUrlId);

}
