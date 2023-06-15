package com.grameen.repayment.mambu.web.payload.responses;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

@Getter
@SuperBuilder
public abstract class MambuServiceResponse {

  private static final String NO_CALL_ON_API = "Mambu API call not completed";

  private HttpStatus httpStatus;
  private String message;

  public final boolean isError() {
    if (null == httpStatus) {
      throw new IllegalStateException(NO_CALL_ON_API);
    }

    return httpStatus.isError();
  }
}