package com.grameen.repayment.mambu.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MambuAddress {

  private String city;
  private String country;
  private String encodedKey;
  private Integer indexInList;
  private Double latitude;
  private String line1;
  private String line2;
  private Double longitude;
  private String parentKey;
  private String postcode;
  private String region;
}
