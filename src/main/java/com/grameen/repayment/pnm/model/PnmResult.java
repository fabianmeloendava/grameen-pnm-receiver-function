package com.grameen.repayment.pnm.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@XmlRootElement(name = "result")
public class PnmResult {

    @XmlElement(name = "payments")
    private Payments payments;


    @XmlAttribute(name = "status")
    private String status;

    @XmlAttribute(name = "warning")
    private String warning;

    @XmlElement(name = "errors")
    private Errors errors;

}
