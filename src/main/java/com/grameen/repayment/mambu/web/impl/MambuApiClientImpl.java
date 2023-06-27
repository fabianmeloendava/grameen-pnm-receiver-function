package com.grameen.repayment.mambu.web.impl;


import com.grameen.repayment.mambu.model.MambuCenter;
import com.grameen.repayment.mambu.model.MambuClient;
import com.grameen.repayment.mambu.web.payload.MambuApiClient;
import com.grameen.repayment.mambu.web.payload.MambuWebClient;
import com.grameen.repayment.mambu.web.payload.responses.MambuGetCenterResponse;
import com.grameen.repayment.mambu.web.payload.responses.MambuGetClientResponse;
import com.grameen.repayment.pnm.web.impl.RetryPolicyProvider;
import jdk.jfr.ContentType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
@Slf4j
public class MambuApiClientImpl implements MambuApiClient {

    private static final String ERROR_ON_MAMBU_API_CALL = "Error on Mambu API call";
    private final MambuWebClient mambuWebClient;
    private final RetryPolicyProvider retryPolicyProvider;

    @Value("${mambu.api.clients.resource}")
    private String mambuApiClientsResource;

    @Value("${mambu.api.centres.resource}")
    private String mambuApiCentresResource;


    public MambuApiClientImpl(MambuWebClient mambuWebClient,
                              RetryPolicyProvider retryPolicyProvider) {
        this.mambuWebClient = mambuWebClient;
        this.retryPolicyProvider = retryPolicyProvider;
    }


    @Override
    public MambuGetClientResponse fetchClient(String clientId) {

        String clientDetailsURI = String.format("/%s/%s", mambuApiClientsResource, clientId);

        var responseBuilder = MambuGetClientResponse.builder();

        try {

            MambuClient mambuClient = mambuWebClient.prepareGetRequest(clientDetailsURI)
                    .retrieve()
                    .onStatus(
                            HttpStatus::is5xxServerError,
                            retryPolicyProvider::onError
                    )
                    .bodyToMono(MambuClient.class)
                    .retryWhen(retryPolicyProvider.getRetryPolicy())
                    .block();

            responseBuilder
                    .httpStatus(HttpStatus.OK)
                    .mambuClient(mambuClient);

        } catch (WebClientResponseException ex) {
            if (HttpStatus.NOT_FOUND != ex.getStatusCode()) {
                log.error(ERROR_ON_MAMBU_API_CALL, ex);
            }

            responseBuilder
                    .httpStatus(ex.getStatusCode())
                    .message(ex.getMessage());

        } catch (Exception ex) {
            log.error(ERROR_ON_MAMBU_API_CALL, ex);

            responseBuilder
                    .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message(ex.getMessage());
        }

        return responseBuilder.build();
    }

    @Override
    public MambuGetCenterResponse fetchCenter(String centerId) {

        String centerDetailsURI = String.format("/%s/%s", mambuApiCentresResource, centerId);

        var responseBuilder = MambuGetCenterResponse.builder();

        try {

            MambuCenter mambuCenter = mambuWebClient.prepareGetRequest(centerDetailsURI)
                    .retrieve()
                    .onStatus(
                            HttpStatus::is5xxServerError,
                            retryPolicyProvider::onError
                    )
                    .bodyToMono(MambuCenter.class)
                    .retryWhen(retryPolicyProvider.getRetryPolicy())
                    .block();

            responseBuilder
                    .httpStatus(HttpStatus.OK)
                    .mambuCenter(mambuCenter);

        } catch (WebClientResponseException ex) {
            if (HttpStatus.NOT_FOUND != ex.getStatusCode()) {
                log.error(ERROR_ON_MAMBU_API_CALL, ex);
            }

            responseBuilder
                    .httpStatus(ex.getStatusCode())
                    .message(ex.getMessage());

        } catch (Exception ex) {
            log.error(ERROR_ON_MAMBU_API_CALL, ex);

            responseBuilder
                    .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message(ex.getMessage());
        }

        return responseBuilder.build();
    }


}


