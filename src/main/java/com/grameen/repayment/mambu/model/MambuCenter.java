package com.grameen.repayment.mambu.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MambuCenter {

    private String encodedKey;
    private String id;
    private String creationDate;
    private String lastModifiedDate;
    private String name;
    private String notes;
    private String state;
    private String meetingDay;
    private String assignedBranchKey;
}
