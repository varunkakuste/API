package com.api.apiserver.model;

import java.util.List;

import lombok.Data;

/**
 * Created by Varun Kakuste on 5/22/18.
 */
@Data
public class Account {
  private String id;
  private List<Profile> profiles;
}
