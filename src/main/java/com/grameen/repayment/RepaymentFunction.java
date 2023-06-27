package com.grameen.repayment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.grameen.repayment.mambu.web.payload.MambuApiClient;
import com.grameen.repayment.mambu.web.payload.responses.MambuGetCenterResponse;
import com.grameen.repayment.mambu.web.payload.responses.MambuGetClientResponse;
import com.grameen.repayment.pnm.PnmService;
import com.grameen.repayment.pnm.model.PNMPayment;
import com.grameen.repayment.pnm.model.PnmDateSegment;
import com.grameen.repayment.pnm.web.exception.PnmException;
import com.grameen.repayment.queue.PnmStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

@Component("repayment-job")
@Slf4j
public class RepaymentFunction implements Function<Mono<String>, Mono<String>> {

    private static final String INFO_START_DATE = "Process to receive the PNM is triggered ";

    private static final String INFO_END_DATE = " to ";
    private static final String INFO_MSG_ID = " Message stored in the queue with Message ID: ";
    private static final String ERROR_JSON = " Error Processing JSON to File in Receiver Function  ";
    private static final String ERROR_GET_PAYMENTS = " Error Processing payments in Receiver Function  ";

    private static final String ERROR_DATES = "The JSON File can't be obtained ";
    @Autowired
    PnmStorageService serviceStorage;
    @Value("${pnm.api.site.identifier}")
    String siteIdentifier;
    @Value("${grameen.storage.queue.queue-name}")
    private String baseQueueName;
    @Autowired
    private PnmService pnmService;
    @Autowired
    private MambuApiClient mambuService;

    public Mono<String> apply(Mono<String> mono) {


        return mono.map(m -> {
            //Obtain dates from File in Azure Storage Container
            PnmDateSegment dates = serviceStorage.getDatesFromContainer();
            if (dates == null) {
                throw new PnmException(ERROR_DATES);
            }
            log.info(INFO_START_DATE + dates.getStartDate() + INFO_END_DATE + dates.getEndDate());

            //Send request to PNM to get the list of payments
            String[] branchKeySplit = siteIdentifier.split(",");
            //Iterate over every branch
            Arrays.stream(branchKeySplit).forEach(key -> {
                List<PNMPayment> payments = pnmService.getPaymentBySegment(key, dates.getStartDate(), dates.getEndDate());
                //Send request to PNM to get the list of payments
                payments.forEach(payment -> {
                    String paymentJson;
                    try {
                        ObjectMapper mapper = new ObjectMapper();
                        //get member key with mambuID (siteCustomerIdentifier) in PNM Payment Info
                        MambuGetClientResponse client = mambuService.fetchClient(payment.getSiteCustomerIdentifier());
                        payment.setMemberKey(client.getMambuClient().getEncodedKey());
                        //Get the center meeting day in centre information
                        MambuGetCenterResponse centre = mambuService.fetchCenter(client.getMambuClient().getAssignedCentreKey());
                        payment.setCentreKey(centre.getMambuCenter().getId());
                        paymentJson = mapper.writeValueAsString(payment);
                        String nameOfDay = centre.getMambuCenter().getMeetingDay().toLowerCase();
                        //Get the name of queue
                        String queueName = String.format(baseQueueName + "-%s", nameOfDay);
                        String res = serviceStorage.sendPaymentToQueue(queueName, paymentJson);
                        log.info(INFO_MSG_ID + res);
                    } catch (JsonProcessingException e) {
                        log.error(ERROR_JSON, e);
                    } catch (Exception e) {
                        log.error(ERROR_GET_PAYMENTS, e);

                    }


                });
            });

            return "Triggered";
        });

    }
}
