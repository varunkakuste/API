package com.api.apiserver.model;

import java.util.Set;

import lombok.Data;

/**
 * Created by Varun Kakuste on 5/22/18.
 */
@Data
public class Marker {
  private String id;
  private Set<Variant> variants;
}
