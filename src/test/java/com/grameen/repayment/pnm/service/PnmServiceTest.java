package com.grameen.repayment.pnm.service;


import com.grameen.repayment.pnm.PnmService;
import com.grameen.repayment.pnm.model.PNMPayment;
import com.grameen.repayment.pnm.web.impl.PnmApiClient;
import com.grameen.repayment.pnm.web.payload.PnmGetReportResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.grameen.repayment.pnm.setup.RepaymentMocker.mockGetReportResponse_Ok200;
import static com.grameen.repayment.pnm.setup.RepaymentMocker.mockGetReportResponse_error500;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PnmServiceTest {

    @InjectMocks
    private PnmService pnmService;

    @Mock
    private PnmApiClient pnmApiClient;


    @Test
    void onGetPaymentByDate_whenActivePayments_thenGetListPaymentResponse() {

        PnmGetReportResponse pnmReportResponse = mockGetReportResponse_Ok200();
        PnmGetReportResponse pnmReportResponseError = mockGetReportResponse_error500();


        when(pnmApiClient.fetchPaymentByDate(any(), any(), any(), any(), anyLong()))
                .thenReturn(pnmReportResponse);
        when(pnmApiClient.fetchPaymentByDate(any(), any(), any(), any(), anyLong()))
                .thenReturn(pnmReportResponseError);
      //  List<PNMPayment> response = pnmService.getPaymentBySegment("05/29/2023 12:03:00","05/29/2023 12:04:00");
       // assertThat(response.get(0).getPaymentAmount()).isEqualTo("105.68");
      //  assertThat(pnmReportResponse.getReport().getPayments().getPaymentList().get(0).getPaymentAmount()).isEqualTo("105.68");
    }


}




