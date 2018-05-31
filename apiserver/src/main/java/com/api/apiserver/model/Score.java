package com.api.apiserver.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * Created by Varun Kakuste on 5/23/18.
 */
@Data
public class Score {
  private Integer numberOfVariants;
  private Double totalDosage;
  private List<String> variants;
  private String marker;

  @JsonIgnore
  public void addVariant(String variant) {
    if (this.variants == null) {
      this.variants = new ArrayList<String>();
    }
    this.variants.add(variant);
  }
}
