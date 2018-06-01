package com.api.apiserver.controller;

import com.api.apiserver.model.Score;
import com.api.apiserver.service.APIService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * Created by Varun Kakuste on 5/19/18.
 */
@RestController
@CrossOrigin("*")
public class ApiController {

  private final APIService apiService;

  @Autowired
  public ApiController(APIService apiService) {
    this.apiService = apiService;
  }

  @RequestMapping(value = "/marker-details", method = RequestMethod.GET)
  public ResponseEntity<Set<Score>> getMarkerDetails(@RequestParam("profileId") String profileId,
                                                     @RequestParam("token") String token)
      throws Exception {
    Set<Score> scores = apiService.calculateScore(profileId, token);
    return new ResponseEntity(scores, HttpStatus.OK);
  }

  @RequestMapping(value = "/error", params = "error_description", method = RequestMethod.GET)
  public ResponseEntity<String> error(@RequestParam("error_description") String errorDescription) throws Exception {
    return new ResponseEntity<String>(errorDescription, HttpStatus.BAD_REQUEST);
  }

  @RequestMapping("/api/welcome")
  public ResponseEntity<String> welcome() {
    return new ResponseEntity<String>("Welcome", HttpStatus.OK);
  }


}
