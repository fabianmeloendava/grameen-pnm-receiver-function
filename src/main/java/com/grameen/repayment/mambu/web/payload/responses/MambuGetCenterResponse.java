package com.grameen.repayment.mambu.web.payload.responses;

import com.grameen.repayment.mambu.model.MambuCenter;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class MambuGetCenterResponse extends MambuServiceResponse {

    private MambuCenter mambuCenter;
}