package com.api.apiserver;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Varun Kakuste on 5/19/18.
 */
@RestController
@CrossOrigin("*")
public class ApiController {

  @RequestMapping("/")
  public ResponseEntity<String> slash(HttpServletRequest request) {
    return new ResponseEntity<String>("Welcome slash", HttpStatus.OK);
  }

  @RequestMapping("/api/welcome")
  public ResponseEntity<String> welcome() {
    return new ResponseEntity<String>("Welcome", HttpStatus.OK);
  }
}
