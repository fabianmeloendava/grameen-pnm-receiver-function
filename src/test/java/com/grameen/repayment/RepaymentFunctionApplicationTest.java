package com.grameen.repayment;

import com.microsoft.azure.functions.ExecutionContext;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.function.adapter.azure.FunctionInvoker;
import reactor.core.publisher.Mono;

import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
 class RepaymentFunctionApplicationTest {


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
                return Logger.getLogger(RepaymentFunctionApplicationTest.class.getName());
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
