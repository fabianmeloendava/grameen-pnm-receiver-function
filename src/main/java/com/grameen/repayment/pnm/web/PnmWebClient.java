package com.grameen.repayment.pnm.web;


import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;

public interface PnmWebClient {

  RequestHeadersSpec<?> prepareGetRequest(String uri);


}
