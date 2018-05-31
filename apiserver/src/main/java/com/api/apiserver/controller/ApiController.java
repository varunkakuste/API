package com.api.apiserver.controller;

import com.api.apiserver.model.AccountData;
import com.api.apiserver.model.Marker;
import com.api.apiserver.model.MarkerData;
import com.api.apiserver.model.OAuthToken;
import com.api.apiserver.model.Profile;
import com.api.apiserver.model.ProfileData;
import com.api.apiserver.service.APIService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
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

  @RequestMapping(value = "/", params = "code", method = RequestMethod.GET)
  public ResponseEntity variantScore(@RequestParam("code") String code) throws Exception {

    // here fetch the variants match it up with the variant.txt, if present score it based on dosage


    return new ResponseEntity(response, HttpStatus.OK);
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
