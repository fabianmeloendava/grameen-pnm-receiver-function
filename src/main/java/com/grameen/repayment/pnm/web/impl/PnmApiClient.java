package com.grameen.repayment.pnm.web.impl;


import com.grameen.repayment.pnm.model.PnmResult;
import com.grameen.repayment.pnm.web.PnmWebClient;
import com.grameen.repayment.pnm.web.exception.PnmException;
import com.grameen.repayment.pnm.web.payload.PnmGetReportResponse;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
@Slf4j
public class PnmApiClient {


    private static final String REPORT_PAYMENTS = "report_payments";
    private static final String START_DATE = "start_date";

    private static final String END_DATE = "end_date";
    private static final String SITE_IDENTIFIER = "site_identifier";
    private static final String TIMESTAMP = "timestamp";
    private static final String VERSION = "version";
    private static final String VERSION_2 = "2.0";
    private static final String SIGNATURE = "signature";
    private static final String ERROR_ON_PNM_API_CALL = "Error on Pay Near Me API call";
    private static final String NOT_FOUND = "Not found error: {}";
    private static final String PNM_FORMAT = "/%s?%s=%s&%s=%s&%s=%s&%s=%s&%s=%s&%s=%s";

    private final PnmWebClient pnmWebClient;
    private final RetryPolicyProvider retryPolicyProvider;
    @Getter
    private PnmResult result;


    public PnmApiClient(PnmWebClient pnmWebClient, RetryPolicyProvider retryPolicyProvider) {
        this.pnmWebClient = pnmWebClient;
        this.retryPolicyProvider = retryPolicyProvider;
    }


    /**
     * Execute request to PNM Query to obtain the List of payments to date segments
     *
     * @param siteIdentifier Branch Identifier
     * @param startDate      start date to query payments
     * @param endDate        end date to query payments
     * @param signature      Signature to consume PNM Service
     * @param timestamp      Timestamp in Unix format
     * @return Response Object
     */
    public PnmGetReportResponse fetchPaymentByDate(
            String siteIdentifier,
            String startDate, String endDate,
            String signature,
            Long timestamp) {

        String pnmRepaymentURI = String.format(
                PNM_FORMAT,
                REPORT_PAYMENTS,
                END_DATE,
                endDate,
                SITE_IDENTIFIER,
                siteIdentifier,
                START_DATE,
                startDate,
                TIMESTAMP,
                timestamp,
                VERSION,
                VERSION_2,
                SIGNATURE,
                signature
        );
        var responseBuilder = PnmGetReportResponse.builder();
        try {
            result = pnmWebClient.prepareGetRequest(pnmRepaymentURI)
                    .retrieve()
                    .onStatus(
                            HttpStatus::is5xxServerError,
                            retryPolicyProvider::onError
                    )
                    .bodyToMono(PnmResult.class)
                    .retryWhen(retryPolicyProvider.getRetryPolicy())
                    .block();
            responseBuilder
                    .httpStatus(HttpStatus.OK)
                    .report(result);
        } catch (WebClientResponseException ex) {
            if (HttpStatus.NOT_FOUND != ex.getStatusCode()) {
                log.error(ERROR_ON_PNM_API_CALL, ex);
            }
            responseBuilder
                    .httpStatus(ex.getStatusCode())
                    .message(ex.getMessage());
        } catch (PnmException ex) {
            log.error(NOT_FOUND, ex.getMessage());
            responseBuilder
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .message(ex.getMessage());
        } catch (Exception ex) {
            log.error(ERROR_ON_PNM_API_CALL, ex);
            responseBuilder
                    .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message(ex.getMessage());
        }
        return responseBuilder.build();
    }
}
