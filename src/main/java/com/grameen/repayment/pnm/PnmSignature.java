package com.grameen.repayment.pnm;


import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class PnmSignature {

    private static final String SIGNATURE_FORMAT =
            "end_date%s" +
                    "site_identifier%s" +
                    "start_date%s" +
                    "timestamp%s" +
                    "version2.0%s";
    private static final String MD5
            = "MD5";
    private static final String HASHING_ERROR = "Error during the Hash md5 creation and service Signature ";

    private PnmSignature() {
        throw new IllegalStateException("PnmSignature");
    }

    /**
     * Responsible to create the PNM Signature encrypt by MD5 in the defined format
     *
     * @param siteIdentifier Branch ID
     * @param startDate      Start date to query PNM payments
     * @param endDate        End date to query PNM payments
     * @param secretKey      Secret key to execute PNM request
     * @return Map of signature and timestamp
     */
    public static Map<String, Long> createSignature(String siteIdentifier, String startDate, String endDate, String secretKey) {
        Long timestamp = Instant.now().getEpochSecond();
        Map<String, Long> mapSignature = new HashMap<>();
        var input = String.format(
                SIGNATURE_FORMAT,
                endDate,
                siteIdentifier,
                startDate,
                timestamp,
                secretKey); //available in the Merchant Administrative Portal
        MessageDigest md;
        try {

            md = MessageDigest.getInstance(MD5);

            byte[] hashBytes = md.digest(input.getBytes());

            StringBuilder stringBuilder = new StringBuilder();
            for (byte bytes : hashBytes) {
                stringBuilder.append(String.format("%02x", bytes));
            }

            String hashString = stringBuilder.toString();
            mapSignature.put(hashString, timestamp);
            return mapSignature;
        } catch (Exception e) {
            log.error(HASHING_ERROR, e);
            mapSignature.put(HASHING_ERROR, timestamp);
            return mapSignature;

        }
    }


}
