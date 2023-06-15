package com.grameen.repayment.mambu.web.payload.responses;


import com.grameen.repayment.mambu.model.MambuBranch;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class MambuGetBranchResponse extends MambuServiceResponse {

  private MambuBranch mambuBranch;
}