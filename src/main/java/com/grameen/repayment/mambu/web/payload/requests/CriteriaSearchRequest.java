package com.grameen.repayment.mambu.web.payload.requests;


import com.grameen.repayment.mambu.web.payload.requests.searchfilter.SearchFilterCriteria;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CriteriaSearchRequest {

  private List<SearchFilterCriteria> filterCriteria;
}