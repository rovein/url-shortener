package com.bobocode.service;

import com.bobocode.dto.ShortenUrlRequest;
import com.bobocode.persistence.ShortenUrlRepository;
import com.bobocode.persistence.entity.ShortenedUrl;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlphanumericShortenUrlService implements ShortenUrlService {

    private final ShortenUrlRepository shortenUrlRepository;

    @Override
    public String shortenUrl(ShortenUrlRequest shortenUrlRequest) {
        String id = RandomStringUtils.randomAlphanumeric(6);

        ShortenedUrl shortenedUrl = new ShortenedUrl();
        shortenedUrl.setId(id);
        shortenedUrl.setOriginalUrl(shortenUrlRequest.url());
        shortenedUrl.setTitle(shortenUrlRequest.title());

        shortenUrlRepository.save(shortenedUrl);
        return id;
    }

    @Override
    public String getOriginalUrl(String shortenUrlId) {
        return shortenUrlRepository.findById(shortenUrlId)
                .map(ShortenedUrl::getOriginalUrl)
                .orElseThrow();
    }

}
