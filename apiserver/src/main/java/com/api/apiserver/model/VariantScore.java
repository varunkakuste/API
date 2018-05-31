package com.api.apiserver.model;

import java.util.Set;

import lombok.Data;

/**
 * Created by Varun Kakuste on 5/23/18.
 */
@Data
public class VariantScore {
  private Integer numberOfVariants;
  private Integer totalDosage;
  private Set<Variant> variants;
}
