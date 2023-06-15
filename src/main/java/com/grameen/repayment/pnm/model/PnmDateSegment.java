package com.grameen.repayment.pnm.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PnmDateSegment {


        private String startDate;
        private String endDate;
        private String fragment;
}
