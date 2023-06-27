package com.grameen.repayment.mambu.web.payload;


import com.grameen.repayment.mambu.web.payload.responses.MambuGetCenterResponse;
import com.grameen.repayment.mambu.web.payload.responses.MambuGetClientResponse;

public interface MambuApiClient {


    MambuGetClientResponse fetchClient(String clientId);

    MambuGetCenterResponse fetchCenter(String centerId);


}
