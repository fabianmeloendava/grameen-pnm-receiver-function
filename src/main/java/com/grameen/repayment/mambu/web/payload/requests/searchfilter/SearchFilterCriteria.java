package com.grameen.repayment.mambu.web.payload.requests.searchfilter;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class SearchFilterCriteria {

  private String field;
  private String operator;
  private String value;
  private List<String> values;
}
