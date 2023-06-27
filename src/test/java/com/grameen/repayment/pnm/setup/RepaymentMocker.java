package com.grameen.repayment.pnm.setup;

import com.grameen.repayment.pnm.model.*;
import com.grameen.repayment.pnm.web.payload.PnmGetReportResponse;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

public class RepaymentMocker {

    public static Map <String, Long> mockSignature() {
        return Map.of(
               "3c92c52a6c728da682aa7bf289462000",Long.valueOf(1686777494)
        );
    }

    public static PnmGetReportResponse mockGetReportResponse_Ok200() {
        return PnmGetReportResponse.builder()
                .httpStatus(HttpStatus.OK)
                .report(mockPNMResult())
                .build();
    }

    public static PnmGetReportResponse mockGetReportResponse_error500() {
        return PnmGetReportResponse.builder()
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .report(mockPNMResult())
                .build();
    }
    public static PnmResult mockPNMResult() {
        return PnmResult
                .builder()
                .payments(mockPayments())
                .errors(mockErrors())
                .build();
    }
    public static Payments mockPayments() {
        return Payments
                .builder()
                .paymentList(mockListPayments())
                .build();
    }
    public static List<PNMPayment> mockListPayments() {
        return List.of(
                PNMPayment
                        .builder()
                        .paymentAmount("105.68")
                        .paymentCurrency("USD")
                        .paymentMade("2023-05-29 23:22:53 -0700")
                        .build()
        );
    }
    public static Errors mockErrors() {
        return Errors
                .builder()
                .error(mockError())
                .build();
    }

    public static PnmError mockError() {
        return PnmError
                .builder()
                .description("Error")
                .build();
    }

}
