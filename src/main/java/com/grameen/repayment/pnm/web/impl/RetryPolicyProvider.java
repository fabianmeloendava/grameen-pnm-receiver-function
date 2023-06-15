package com.grameen.repayment.pnm.web.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;
import reactor.util.retry.RetryBackoffSpec;

import java.time.Duration;

@Component
public class RetryPolicyProvider {

  private static final int RETRY_ATTEMPTS = 3;
  private static final int RETRY_INTERVAL = 2;
  private static final double RETRY_JITTER = 0.77;

  private static final String EXTERNAL_SERVICE_ERROR = "External service error";
  private static final String RETRY_ERROR_MESSAGE = "Call to external API failed after max retries";

  public Mono<? extends Throwable> onError(ClientResponse response) {
    return Mono.error(
        new ExternalServiceException(EXTERNAL_SERVICE_ERROR, response.rawStatusCode()));
  }

  public RetryBackoffSpec getRetryPolicy() {
    return Retry.backoff(RETRY_ATTEMPTS, Duration.ofSeconds(RETRY_INTERVAL))
        .jitter(RETRY_JITTER)
        .filter(ExternalServiceException.class::isInstance)
        .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) -> {
          throw new ExternalServiceException(RETRY_ERROR_MESSAGE,
              HttpStatus.SERVICE_UNAVAILABLE.value());
        });
  }
}
