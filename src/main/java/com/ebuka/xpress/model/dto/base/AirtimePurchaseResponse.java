package com.ebuka.xpress.model.dto.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class AirtimePurchaseResponse {
    private int requestId;
    private String referenceId;
    private String responseCode;
    private String responseMessage;
    private Object data;

}
