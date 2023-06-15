package com.grameen.repayment.mambu.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MambuRegulatoryInformationBranches {
  @JsonProperty("PNMSKEY")
  private String pnmskey;
  @JsonProperty("PNMBID")
  private String pnmbid;


}
