package com.grameen.repayment.mambu.web.payload.requests.searchfilter;

import lombok.Getter;

@Getter
public enum SearchFilterField {

  TRANSACTION_TYPE("type"),

  ACCOUNT_HOLDER_KEY("accountHolderKey"),
  ACCOUNT_STATE("accountState"),
  CENTER_KEY("centreKey"),
  PARENT_ACCOUNT_KEY("parentAccountKey"),
  MOBILE_PHONE_NUMBER("mobilePhoneNumber"),
  PRODUCT_TYPE_KEY("productTypeKey");

  private final String name;

  SearchFilterField(String name) {
    this.name = name;
  }
}