package com.bobocode.persistence;

import com.bobocode.persistence.entity.ShortenedUrl;
import org.springframework.data.repository.CrudRepository;

public interface ShortenUrlRepository extends CrudRepository<ShortenedUrl, String> {
}
