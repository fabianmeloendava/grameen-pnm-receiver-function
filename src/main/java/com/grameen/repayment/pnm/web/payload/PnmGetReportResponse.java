package com.grameen.repayment.pnm.web.payload;


import com.grameen.repayment.pnm.model.PnmResult;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@SuperBuilder
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PnmGetReportResponse extends PnmServiceResponse {

  private PnmResult report;

}
