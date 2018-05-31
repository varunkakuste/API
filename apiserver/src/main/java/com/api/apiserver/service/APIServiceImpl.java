package com.api.apiserver.service;

import com.api.apiserver.model.Marker;
import com.api.apiserver.model.MarkerData;
import com.api.apiserver.model.Score;
import com.api.apiserver.model.Variant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Varun Kakuste.
 */
@Component
public class APIServiceImpl implements APIService  {

  public static final String BASE_URL = "https://api.23andme.com";

  private final RestTemplate restTemplate;
  private static Set<String> neanderthalVariants;
  private static Set<String> accessionIds;

  @Autowired
  public APIServiceImpl(RestTemplate restTemplate) {
    loadNeanderthalVariants();
    this.restTemplate = restTemplate;
  }

  private void loadNeanderthalVariants() {
    neanderthalVariants = new HashSet<String>();
    accessionIds = new HashSet<String>();
    ResourceLoader resourceLoader = new FileSystemResourceLoader();
    Resource resource =
        resourceLoader.getResource("classpath:" + "/files/neanderthal_variants.txt");
    InputStream fileStream = null;
    BufferedReader br = null;
    try {
      fileStream = resource.getInputStream();
      br = new BufferedReader(new InputStreamReader(fileStream));
      String strLine;
      while ((strLine = br.readLine()) != null) {
        neanderthalVariants.add(strLine);
        accessionIds.add(strLine.split(":")[0]);
      }
    } catch (Exception e) {
      System.out.println("Problem loading variants and accession_ids");
    } finally {
      try {
        fileStream.close();
        br.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    System.out.println("LOADED");
  }


  public Set<Score> calculateScore() throws Exception {
    Map<String, List<Variant>> neanderthalVariantsOfMarker = new HashMap<String, List<Variant>>();
    for (String accessionId: accessionIds) {
      // get markers by accessionId
      Set<Marker> markers = getMarkersByAccessionId(accessionId);
      getVariantsAssociatedWithMarker(neanderthalVariantsOfMarker, markers);
    }
    // calculate score
    Set<Score> scores = getScores(neanderthalVariantsOfMarker);
    return scores;
  }

  private void getVariantsAssociatedWithMarker(
      Map<String, List<Variant>> neanderthalVariantsOfMarker, Set<Marker> markers) {
    if (markers != null && !markers.isEmpty()) {
      //iterate over markers and look for neanderthal variants
      for (Marker marker: markers) {
        List<Variant> variants = marker.getVariants();
        if (variants != null && !variants.isEmpty()) {
          // iterate over marker variants and check if it has neanderthal variant from text file
          // if so, add it to the neanderthalVariantsOfMarker
          for (Variant variant: variants) {
            if (neanderthalVariants.contains(variant.getVariantAsAString())) {
              if (neanderthalVariantsOfMarker.containsKey(marker.getId())) {
                neanderthalVariantsOfMarker.get(marker.getId()).add(variant);
              } else {
                List<Variant> variantList = new ArrayList<Variant>();
                variantList.add(variant);
                neanderthalVariantsOfMarker.put(marker.getId(), variantList);
              }
            }
          }
        }
      }
    }
  }

  // We don't really require this method if we calculate score in
  // getVariantsAssociatedWithMarker() method but I want it to isolate it for now
  // just in case we have to make any other changes or require more information later
  private Set<Score> getScores(Map<String, List<Variant>> neanderthalVariantsOfMarker) {
    Set<Score> scores = new HashSet<Score>();
    Score score = null;
    if (neanderthalVariantsOfMarker != null && !neanderthalVariantsOfMarker.isEmpty()) {
      for (String marker: neanderthalVariantsOfMarker.keySet()) {
        List<Variant> variants = neanderthalVariantsOfMarker.get(marker);
        score = new Score();
        score.setMarker(marker);
        score.setNumberOfVariants(variants.size());
        Double dosage = 0.0;
        for (Variant variant: variants) {
          score.addVariant(variant.getVariantAsAString());
          if (variant.getDosage() != null) {
            dosage += variant.getDosage();
          }
        }
        score.setTotalDosage(dosage);
        scores.add(score);
      }
    }
    return scores;
  }

  public Set<Marker> getMarkersByAccessionId(String accessionId) throws Exception {
    Set<Marker> result = null;
    try {
      HttpEntity entity = new HttpEntity<MultiValueMap>(getDemoHeaders());
      URI uri = getUri("/3/profile/demo_profile_id/marker/", accessionId);
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

  private HttpHeaders getDemoHeaders() {
    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", "Bearer demo_oauth_token");
    return headers;
  }

  private URI getUri(String path, String accessionId) {
    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(BASE_URL + path);
    if (!StringUtils.isEmpty(accessionId)) {
      // we can accept Map for query parameters instead of accessionId
      builder.queryParam("accession_id", accessionId);
    }
    return builder.build().encode().toUri();
  }

}
