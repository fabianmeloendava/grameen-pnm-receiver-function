package com.grameen.repayment.pnm.web.impl;


import com.grameen.repayment.pnm.web.PnmWebClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.codec.xml.Jaxb2XmlDecoder;
import org.springframework.http.codec.xml.Jaxb2XmlEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;

import javax.annotation.PostConstruct;

@Service
@Slf4j
public class PnmWebClientImpl implements PnmWebClient {

    @Value("${pnm.api.endpoint}")
    private String pnmApiEndpoint;


    private WebClient webClient;

    @PostConstruct
    private void setUp() {

        this.webClient = WebClient.builder()
                .baseUrl(pnmApiEndpoint)
                //  .clientConnector(new ReactorClientHttpConnector(baseHttpClient))
                .exchangeStrategies(ExchangeStrategies.builder().codecs(configurer -> {
                    configurer.defaultCodecs().jaxb2Encoder(new Jaxb2XmlEncoder());
                    configurer.defaultCodecs().jaxb2Decoder(new Jaxb2XmlDecoder());
                }).build())
                .build();
    }

    public RequestHeadersSpec<?> prepareGetRequest(String uri) {
        return webClient.get()
                .uri(uri)
                .accept(MediaType.APPLICATION_XML);
    }


}
