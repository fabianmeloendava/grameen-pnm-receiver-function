package com.grameen.repayment.mambu.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MambuClient {

    public String encodedKey;
    public String id;
    public String state;
    public Date creationDate;
    public Date lastModifiedDate;
    public Date activationDate;
    public Date approvedDate;
    public String firstName;
    public String lastName;
    public String mobilePhone;
    public String preferredLanguage;
    public String birthDate;
    public String assignedBranchKey;
    public String assignedCentreKey;
    public String assignedUserKey;
    public String profilePictureKey;
    public String clientRoleKey;
    public int loanCycle;
    public int groupLoanCycle;

}