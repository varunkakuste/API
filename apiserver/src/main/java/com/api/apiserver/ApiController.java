package com.api.apiserver;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Varun Kakuste on 5/19/18.
 */
@RestController
@RequestMapping("/api")
public class ApiController {
  @RequestMapping("/welcome")
  public ResponseEntity<String> welcome() {
    return new ResponseEntity<String>("Welcome", HttpStatus.OK);
  }
}
