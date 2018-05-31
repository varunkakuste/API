package com.api.apiserver.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Created by Varun Kakuste on 5/22/18.
 */
@Data
public class Variant {
  @JsonProperty("accession_id")
  private String accessionId;
  private Integer start;
  private Integer end;
  private Character allele;
  private Float dosage;

  public String getVariantAsAString() {
    StringBuilder builder = new StringBuilder();
    builder.append(this.accessionId)
        .append(":")
        .append(this.start)
        .append("-")
        .append(this.end)
        .append(":")
        .append(this.allele);
    return builder.toString();
  }

}
