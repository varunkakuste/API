package com.api.apiserver.service;

import com.api.apiserver.model.Marker;
import com.api.apiserver.model.Score;

import java.util.Set;

/**
 * @author Varun Kakuste.
 */
public interface APIService {
  Set<Score> calculateScore() throws Exception;
  Set<Marker> getMarkersByAccessionId(String accessionId) throws Exception;
}
