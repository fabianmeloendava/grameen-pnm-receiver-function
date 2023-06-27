package com.grameen.repayment.mambu.web.impl;

import com.grameen.repayment.mambu.web.payload.MambuWebClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestBodySpec;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;

import javax.annotation.PostConstruct;

@Service
public class MambuWebClientImpl implements MambuWebClient {

  private static final String API_KEY_HEADER = "apiKey";

  @Value("${mambu.api.endpoint}")
  private String mambuApiEndpoint;
  @Value("${mambu.api.key}")
  private String mambuApiKey;
  @Value("${mambu.api.accept.json}")
  private String mambuApiAcceptJson;

  private WebClient webClient;

  @PostConstruct
  private void setUp() {
     WebClient.builder().baseUrl(mambuApiEndpoint).codecs(
        clientCodecConfigurer -> clientCodecConfigurer.defaultCodecs()
            .maxInMemorySize(16 * 1024 * 1024)
    ).build();

    this.webClient = WebClient.create(mambuApiEndpoint);
  }

  public RequestHeadersSpec<?> prepareGetRequest(String uri) {
    return webClient.get()
        .uri(uri)
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .header(API_KEY_HEADER, mambuApiKey)
        .accept(MediaType.valueOf(mambuApiAcceptJson));
  }

  public RequestBodySpec preparePostRequest(String uri) {
    return webClient.post()
        .uri(uri)
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .header(API_KEY_HEADER, mambuApiKey)
        .accept(MediaType.valueOf(mambuApiAcceptJson));
  }
}
