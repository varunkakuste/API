package com.api.apiserver.service;

import com.api.apiserver.model.Marker;
import com.api.apiserver.model.Score;

import java.util.Set;

/**
 * @author Varun Kakuste.
 */
public interface APIService {
  Set<Score> calculateScore(String profileId, String token) throws Exception;
  Set<Marker> getMarkersByAccessionId(String accessionId, String profileId, String token)
      throws Exception;
}
