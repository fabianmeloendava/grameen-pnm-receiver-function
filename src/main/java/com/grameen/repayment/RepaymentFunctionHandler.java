package com.grameen.repayment;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.TimerTrigger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.function.adapter.azure.FunctionInvoker;

@Slf4j
public class RepaymentFunctionHandler extends FunctionInvoker<String, String> {

    static final String INFO_FUNCTION = "Receiver Function is triggered: ";

    /**
     * Schedule Function to receive payment from PNM and send it to Storage Queue
     *
     * @param timerInfo Timer Information
     * @param context   Execution context
     * @return String with timer info and context
     */
    @FunctionName("repayment-job")
    public String runOnSchedule(
            @TimerTrigger(name = "myScheduledTaskTrigger", schedule = "%schedule%") String timerInfo,
            ExecutionContext context) {
        log.info(INFO_FUNCTION + timerInfo);
        return handleRequest(timerInfo, context);
    }
}
