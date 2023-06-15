package com.grameen.repayment.mambu.model;

import lombok.Getter;

@Getter
public enum MambuBranchState {

  ACTIVE("ACTIVE"), INACTIVE("INACTIVE");

  private final String name;

  MambuBranchState(String name) {
    this.name = name;
  }
}
