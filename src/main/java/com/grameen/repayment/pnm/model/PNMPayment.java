package com.grameen.repayment.pnm.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAttribute;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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


}
