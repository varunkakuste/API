package com.api.apiserver.service;

import com.api.apiserver.model.Account;
import com.api.apiserver.model.Marker;
import com.api.apiserver.model.OAuthToken;
import com.api.apiserver.model.Profile;
import com.api.apiserver.model.VariantScore;

import java.util.Set;

/**
 * Created by Varun Kakuste on 5/22/18.
 */
public interface APIService {
  OAuthToken generateOAuthToken(String code) throws Exception;
  VariantScore calculateScore(OAuthToken oAuthToken) throws Exception;
  Account getAccount(OAuthToken oAuthToken) throws Exception;
  Profile getProfile(OAuthToken oAuthToken, String profileId) throws Exception;
  Set<Marker> getMarkers(OAuthToken oAuthToken, String profileId) throws Exception;
}
