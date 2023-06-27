package com.grameen.repayment.pnm.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static com.grameen.repayment.pnm.PnmSignature.createSignature;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
 class PnmSignatureTest {

/**
    // JUnit test for saveEmployee method
    @DisplayName("JUnit test for Signature method")
    @Test
    void givenVariables_whenExecuteSignature_thenReturnSignature() {
        Map<String, Long> mapResult = createSignature("S3677359566", "05/29/2023 12:03:00", "05/29/2023 12:04:00", "15bb9f9ae8a2e6dcbdd315326");
        assertThat(mapResult.get("3c92c52a6c728da682aa7bf289462000")).isEqualTo(Long.valueOf(1686777494));
    }**/
}