package com.grameen.repayment.mambu.web.payload.requests.searchfilter;

import lombok.Getter;

@Getter
public enum SearchFilterOperator {

  EQUALS("EQUALS"),
  IN("IN");

  private final String name;

  SearchFilterOperator(String name) {
    this.name = name;
  }
}