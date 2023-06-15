package com.grameen.repayment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.config.EnableIntegration;

@SpringBootApplication
@EnableIntegration
public class RepaymentFunctionApplication {

    public static void main(String[] args)  {
        SpringApplication.run(RepaymentFunctionApplication.class, args);
    }


}
