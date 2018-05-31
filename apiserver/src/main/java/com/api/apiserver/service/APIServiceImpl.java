package com.api.apiserver.service;

import com.api.apiserver.model.Account;
import com.api.apiserver.model.AccountData;
import com.api.apiserver.model.Marker;
import com.api.apiserver.model.MarkerData;
import com.api.apiserver.model.OAuthToken;
import com.api.apiserver.model.Profile;
import com.api.apiserver.model.ProfileData;
import com.sun.xml.internal.bind.v2.TODO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Varun Kakuste on 5/22/18.
 */
@Component
public class APIServiceImpl implements APIService  {

  public static final String BASE_URL = "https://api.23andme.com";

  private final Set<String> NEANDERTHAL_VARIANTS;
  private final RestTemplate restTemplate;


  @Autowired
  public APIServiceImpl(RestTemplate restTemplate) {
    this.NEANDERTHAL_VARIANTS = loadNeanderthalVariants();
    this.restTemplate = restTemplate;
  }

  private Set<String> loadNeanderthalVariants() {
    ResourceLoader resourceLoader = new FileSystemResourceLoader();
    Resource resource =
        resourceLoader.getResource("classpath:" + "/files/neanderthal_variants.txt");
    InputStream fileStream = null;
    BufferedReader br = null;
    Set<String> result = new HashSet<String>();
    try {
      fileStream = resource.getInputStream();
      br = new BufferedReader(new InputStreamReader(fileStream));
      String strLine;
      while ((strLine = br.readLine()) != null)   {
        result.add(strLine);
      }
    } catch (Exception e) {
    } finally {
      try {
        fileStream.close();
        br.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return result;
  }

  @Override
  public OAuthToken generateOAuthToken(String code) throws Exception {
    OAuthToken result = null;

    MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
    map.add("client_id", "");
    map.add("client_secret", "");
    map.add("grant_type", "authorization_code");
    map.add("code", code);
    map.add("redirect_uri", "http://localhost:8080");
    map.add("scope", "basic rs3094315 names email");

    try {
      HttpEntity entity = new HttpEntity<MultiValueMap<String, String>>(map, new HttpHeaders());
      URI uri = getUri("/token/");
      ResponseEntity<OAuthToken> response =
          restTemplate.exchange(uri, HttpMethod.POST, entity, OAuthToken.class);
      result = response.getBody();
    } catch (HttpClientErrorException exp) {
      throw new Exception("Exception while generating a token: " + exp.getResponseBodyAsString());
    }
    return result;
  }






  @Override
  public Account getAccount(OAuthToken oAuthToken) throws Exception {
    Account result = null;
    try {
      HttpEntity entity = new HttpEntity<MultiValueMap>(getHeaders(oAuthToken));
      URI uri = getUri("/3/account/");
      ResponseEntity<AccountData> response =
          restTemplate.exchange(uri, HttpMethod.GET, entity, AccountData.class);
      AccountData accountData = response.getBody();
      if (accountData != null &&
          accountData.getData() != null &&
          !accountData.getData().isEmpty()) {
        result = accountData.getData().get(0);
      }
    } catch (HttpClientErrorException exp) {
      throw new Exception("Exception fetching Account details: " + exp.getResponseBodyAsString());
    }
    return result;
  }





  @Override
  public Profile getProfile(OAuthToken oAuthToken, String profileId) throws Exception {
    Profile result = null;
    try {
      HttpEntity entity = new HttpEntity<MultiValueMap>(getHeaders(oAuthToken));
      URI uri = getUri("/3/profile/" + profileId);
      ResponseEntity<ProfileData> response =
          restTemplate.exchange(uri, HttpMethod.GET, entity, ProfileData.class);
      ProfileData profileData = response.getBody();
      if (profileData != null &&
          profileData.getData() != null &&
          !profileData.getData().isEmpty()) {
        result = profileData.getData().get(0);
      }
    } catch (HttpClientErrorException exp) {
      throw new Exception("Exception fetching Profile details: " + exp.getResponseBodyAsString());
    }
    return result;
  }







  // TODO(varun): This doesn't work unless we pass accession_id/start/end in the query params
  @Override
  public Set<Marker> getMarkers(OAuthToken oAuthToken, String profileId) throws Exception {
    Set<Marker> result = null;
    try {
      HttpEntity entity = new HttpEntity<MultiValueMap>(getHeaders(oAuthToken));
      URI uri = getUri("/3/profile/" + profileId + "/marker/");
      ResponseEntity<MarkerData> response =
          restTemplate.exchange(uri, HttpMethod.GET, entity, MarkerData.class);
      MarkerData markerData = response.getBody();
      if (markerData != null &&
          markerData.getData() != null) {
        result = markerData.getData();
      }
    } catch (HttpClientErrorException exp) {
      throw new Exception("Exception fetching Markers of the profile: " +
          exp.getResponseBodyAsString());
    }
    return result;
  }










  private URI getUri(String path) {
    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(BASE_URL + path);
    return builder.build().encode().toUri();
  }

  private HttpHeaders getHeaders(OAuthToken oAuthToken) {
    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", "Bearer " + oAuthToken.getAccessToken());
    return headers;
  }
}
