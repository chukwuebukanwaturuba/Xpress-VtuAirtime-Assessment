package com.ebuka.xpress.service.impl;

import com.ebuka.xpress.model.dto.base.AirtimePurchaseResponse;
import com.ebuka.xpress.model.dto.base.AirtimeVTURequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.xml.bind.SchemaOutputResolver;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
/**
 * Service responsible for making calls to the Xpress third-party API for airtime VTU requests.
 * Handles HMAC calculation, payment hash computation, and API requests.
 *
 * @author ChukwuEbuka
 * @description Handles communication with the Xpress third-party API.
 */
@RequiredArgsConstructor
@Service
public class XpressThirdPartyApiCall {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    @Value("${application.xpress.secretKey}")
    private String secretKey;

    @Value("${application.xpress.publicKey}")
    private String publicKey;

    @Value("${application.xpress.airtimeVTU.url}")
    private String vtuUrl;


    /**
     * Constructs the XpressThirdPartyApiCall service with RestTemplate and ObjectMapper instances.
     *
     * @param restTemplate RestTemplate instance for HTTP requests
     * @param objectMapper ObjectMapper instance for JSON handling
     */
    public String calculateHMAC512(String data, String key) {
        String HMAC_SHA512 = "HmacSHA512";

        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), HMAC_SHA512);
        Mac mac = null;
        try {
            mac = Mac.getInstance(HMAC_SHA512);
            mac.init(secretKeySpec);

            return Hex.encodeHexString(mac.doFinal(data.getBytes()));
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

     public String computePaymentHash(AirtimeVTURequest airtimeRequest){
         try {
             String requestBody = objectMapper.writeValueAsString(airtimeRequest);
             return calculateHMAC512(requestBody, secretKey);
         } catch (JsonProcessingException e) {
             throw new RuntimeException(e);
         }
     }

     public AirtimePurchaseResponse makeCallToXpress(AirtimeVTURequest airtimeVTURequest){
        String paymentHash = computePaymentHash(airtimeVTURequest);
         HttpHeaders headers = new HttpHeaders();
         headers.setContentType(MediaType.APPLICATION_JSON);
         headers.set("Authorization", "Bearer " + publicKey);
         headers.set("PaymentHash", paymentHash);
//         headers.set("Channel", "API");

         // Create the HTTP entity with headers and body
         HttpEntity<AirtimeVTURequest> entity = new HttpEntity<>(airtimeVTURequest, headers);

         // Define the endpoint URL
         // Send the HTTP POST request
         ResponseEntity<AirtimePurchaseResponse> response = restTemplate.exchange(
                 vtuUrl,
                 HttpMethod.POST,
                 entity,
                 AirtimePurchaseResponse.class
         );
         if(response.getStatusCode().is2xxSuccessful()){
             return response.getBody();
         }
         throw new RuntimeException("Call to xpress failed");
     }

}
