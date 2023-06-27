package com.grameen.repayment.mambu.web.impl;

import lombok.Getter;

@Getter
public class ExternalServiceException extends RuntimeException {

  private final int statusCode;

  public ExternalServiceException(String message, int statusCode) {
    super(message);
    this.statusCode = statusCode;
  }
}
