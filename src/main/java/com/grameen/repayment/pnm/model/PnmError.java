package com.grameen.repayment.pnm.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAttribute;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PnmError {
    @XmlAttribute(name = "description")
    private String description;

}
