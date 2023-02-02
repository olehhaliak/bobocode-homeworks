package com.bobocode.homeworks.olehhaliak.scheduled;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.cache.Cache;

@Component
@RequiredArgsConstructor
@Slf4j
public class CacheInvalidator {
  private final CacheManager cacheManager;

  @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.HOURS)
  private void clearCache() {
    log.info("Invalidating caches...");
    cacheManager.getCacheNames().stream()
        .map(cacheManager::getCache)
        .forEach(Cache::invalidate);

  }
}
