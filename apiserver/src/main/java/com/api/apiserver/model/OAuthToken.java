package com.api.apiserver.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Created by Varun Kakuste on 5/20/18.
 */
@Data
public class OAuthToken {
  @JsonProperty("access_token")
  private String accessToken;

  @JsonProperty("token_type")
  private String tokenType;

  @JsonProperty("expires_in")
  private int expiresIn;

  @JsonProperty("refresh_token")
  private String refreshToken;

  private String scope;
}
