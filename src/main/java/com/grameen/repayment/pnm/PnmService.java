package com.grameen.repayment.pnm;


import com.grameen.repayment.pnm.model.PNMPayment;
import com.grameen.repayment.pnm.web.impl.PnmApiClient;
import com.grameen.repayment.pnm.web.payload.PnmGetReportResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.grameen.repayment.pnm.PnmSignature.createSignature;


@Service
@Slf4j
public class PnmService {


    private final PnmApiClient pnmApiClient;


    @Value("${pnm.api.site.identifier}")
    String siteIdentifier;
    @Value("${pnm.api.site.signature}")
    String secretKey;

    private static final String ERROR_PNM_SERVICE = "Error consuming PNM Service ";

    public PnmService(PnmApiClient pnmApiClient) {
        this.pnmApiClient = pnmApiClient;
    }

    /**
     * * Get all PNM payment by time segment
     *
     * @param startDate start date to query payments
     * @param endDate   end date to query payments
     * @return List of payments
     */
    public List<PNMPayment> getPaymentBySegment(String startDate, String endDate) {
        var signatureFinal = createSignature(siteIdentifier, startDate, endDate, secretKey);
        try {
            if (signatureFinal.size() == 0) {
                return new ArrayList<>();
            }
            Optional<Map.Entry<String, Long>> entry = signatureFinal.entrySet().stream().findAny();
            PnmGetReportResponse response = pnmApiClient.fetchPaymentByDate(siteIdentifier, startDate, endDate, entry.map(Map.Entry::getKey).orElse(null),
                    entry.map(Map.Entry::getValue).orElse(null));
            if (response.getReport() == null) {
                log.error(response.getHttpStatus().toString());
            }

            if ("ok".equals(response.getReport().getStatus())) {
                return response.getReport().getPayments().getPaymentList();
            } else {
                log.error(response.getReport().getErrors().getError().getDescription());
                return new ArrayList<>();
            }
        } catch (Exception e) {
            log.error(ERROR_PNM_SERVICE,e);
        }


        return new ArrayList<>();
    }


}
