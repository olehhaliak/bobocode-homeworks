package com.bobocode.homeworks.olehhaliak.controller;

import com.bobocode.homeworks.olehhaliak.service.LargestIMageService;
import java.net.URI;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LargestImageController {

  private final LargestIMageService largestIMageService;

  @GetMapping("/mars/pictures/largest/{sol}")
  public ResponseEntity<byte[]> getLargestImage(@PathVariable("sol") int sol) {
    log.info("New request for largest picture! Sol = " + sol);
    URI largestImageUri = largestIMageService.getLargestPictureURL(sol);
    byte[] image = largestIMageService.getImageByURL(largestImageUri);

    return ResponseEntity.ok()
        .contentType(MediaType.IMAGE_JPEG)
        .contentLength(image.length)
        .body(image);
  }


  @ExceptionHandler
  public ResponseEntity<String> handleException(NoSuchElementException ex) {
    return new ResponseEntity<>("No picture was found for this sol", HttpStatus.NOT_FOUND);
  }
}
