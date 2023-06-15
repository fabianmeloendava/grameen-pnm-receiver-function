package com.grameen.repayment.pnm.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAttribute;

@Getter
@NoArgsConstructor
public class PnmError {
    @XmlAttribute(name = "description")
    private String description;

}
