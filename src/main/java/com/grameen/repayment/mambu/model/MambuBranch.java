package com.grameen.repayment.mambu.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MambuBranch {

  private String creationDate;
  private String emailAddress;
  private String encodedKey;
  private String id;
  private String lastModifiedDate;
  private String name;
  private String notes;
  private String phoneNumber;
  private MambuBranchState state;
  private List<MambuAddress> addresses;
  @JsonProperty("Regulatory_Information_Branches")
  private MambuRegulatoryInformationBranches regulatoryInformationBranches;
}
