package com.grameen.repayment.mambu.web.payload.responses;


import com.grameen.repayment.mambu.model.MambuClient;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class MambuGetClientResponse extends MambuServiceResponse {

  private MambuClient mambuClient;
}