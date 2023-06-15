package com.grameen.repayment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.grameen.repayment.pnm.PnmService;
import com.grameen.repayment.pnm.model.PNMPayment;
import com.grameen.repayment.pnm.model.PnmDateSegment;
import com.grameen.repayment.pnm.web.exception.PnmException;
import com.grameen.repayment.queue.PnmStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

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
    @Autowired
    private PnmService pnmService;

    public Mono<String> apply(Mono<String> mono) {


        return mono.map(m -> {
            //Obtain dates from File in Azure Storage Container
            PnmDateSegment dates = serviceStorage.getDatesFromContainer();
            if(dates == null){
                throw new PnmException(ERROR_DATES);
            }
            log.info(INFO_START_DATE + dates.getStartDate() + INFO_END_DATE + dates.getEndDate());
            //Send request to PNM to get the list of payments
            List<PNMPayment> payments = pnmService.getPaymentBySegment(dates.getStartDate(), dates.getEndDate());
            //Send request to PNM to get the list of payments
            payments.forEach(payment -> {
                String paymentJson;
                try {
                    ObjectMapper mapper = new ObjectMapper();
                    paymentJson = mapper.writeValueAsString(payment);
                    String res = serviceStorage.sendPaymentToQueue(paymentJson);
                    log.info(INFO_MSG_ID + res);
                } catch (JsonProcessingException e) {
                    log.error(ERROR_JSON, e);
                } catch (Exception e) {
                    log.error(ERROR_GET_PAYMENTS, e);

                }


            });

            return "Triggered";
        });

    }
}
