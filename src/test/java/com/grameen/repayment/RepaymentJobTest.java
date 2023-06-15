package com.grameen.repayment;

import com.grameen.repayment.pnm.PnmService;
import com.microsoft.azure.functions.ExecutionContext;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.cloud.function.adapter.azure.FunctionInvoker;
import reactor.core.publisher.Mono;

import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.assertThat;

public class RepaymentJobTest {

    @Mock
    PnmService pnmService;

    @Test
    private void test() {
        Mono<String> result = new RepaymentFunction().apply(Mono.just(new String("Triggered")));
        assertThat(result.block()).isEqualTo("Triggered");
    }

    @Test
    private void start() {
        FunctionInvoker<String, String> handler = new FunctionInvoker<>(
                RepaymentFunction.class);
        String result = handler.handleRequest(new String("Triggered"), new ExecutionContext() {
            @Override
            public Logger getLogger() {
                return Logger.getLogger(RepaymentJobTest.class.getName());
            }

            @Override
            public String getInvocationId() {
                return "id1";
            }

            @Override
            public String getFunctionName() {
                return "Triggered";
            }
        });
        handler.close();
        assertThat(result).isEqualTo("Triggered");
    }
}
