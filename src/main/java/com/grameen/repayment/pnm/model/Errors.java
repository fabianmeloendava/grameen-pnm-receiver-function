package com.grameen.repayment.pnm.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlElement;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Errors {


    @XmlElement(name = "error")
    private PnmError error;
}
