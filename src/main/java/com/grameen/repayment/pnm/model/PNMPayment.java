package com.grameen.repayment.pnm.model;

import lombok.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class PNMPayment {

    @XmlAttribute(name = "payment_made")
    private String paymentMade;

    @XmlAttribute(name = "payment_amount")
    private String paymentAmount;

    @XmlAttribute(name = "payment_currency")
    private String paymentCurrency;

    @XmlAttribute(name = "payment_status")
    private String paymentStatus;

    @XmlAttribute(name = "payment_type")
    private String paymentType;

    @XmlAttribute(name = "payment_processing_fee")
    private String paymentProcessingFee;

    @XmlAttribute(name = "pnm_payment_identifier")
    private String pnmPaymentIdentifier;

    @XmlAttribute(name = "retailer_identifier")
    private String retailerIdentifier;

    @XmlAttribute(name = "site_customer_identifier")
    private String siteCustomerIdentifier;

    private String centreKey;

    private String memberKey;


}
