package com.bobocode.homeworks.olehhaliak.service;

import com.bobocode.homeworks.olehhaliak.model.Photo;
import com.bobocode.homeworks.olehhaliak.model.PhotoList;
import java.net.URI;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
@Slf4j
public class LargestIMageService {
  private static final String BASE_URL =
      "https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos";

  private final UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(BASE_URL)
      .queryParam("sol", "{sol}")
      .queryParam("api_key", "DEMO_KEY");
  private final RestTemplate template;

  @Cacheable("largest-image-url")
  public URI getLargestPictureURL(int sol) {
    log.info("Looking for the largest picture URL... Sol = " + sol);
    PhotoList photoList = template.getForObject(uriBuilder.build(sol), PhotoList.class);

    Map<String, Long> urlToSize = photoList.photos().stream()
        .map(Photo::imageSrc)
        .collect(Collectors.toMap(url -> url, this::getSizeForPhoto));

    String biggestImageUrl = urlToSize.entrySet().stream()
        .max(Map.Entry.comparingByValue())
        .orElseThrow()
        .getKey();

    return URI.create(biggestImageUrl);
  }

  @Cacheable("image")
  public byte[] getImageByURL(URI url) {
    log.info("Reading picture from URL : " + url.toASCIIString());
    String redirectedURL = template.headForHeaders(url).getFirst("Location");
    return template.getForObject(redirectedURL, byte[].class);
  }


  private long getSizeForPhoto(String url) {
    return template.headForHeaders(URI.create(url)).getContentLength();
  }
}
