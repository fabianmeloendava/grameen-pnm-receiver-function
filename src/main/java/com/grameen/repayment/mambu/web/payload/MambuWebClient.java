package com.grameen.repayment.mambu.web.payload;

import org.springframework.web.reactive.function.client.WebClient.RequestBodySpec;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;

public interface MambuWebClient {

  RequestHeadersSpec<?> prepareGetRequest(String URI);

  RequestBodySpec preparePostRequest(String URI);

}
