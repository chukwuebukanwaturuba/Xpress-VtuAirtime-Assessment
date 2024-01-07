package com.ebuka.xpress.model.dto.base;

import com.ebuka.xpress.exception.InvalidCredentialsException;
import com.ebuka.xpress.model.enums.Biller;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.SecureRandom;

@Data
@NoArgsConstructor
public class AirtimeVTURequest {
    private String requestId;
    private String uniqueCode;
    private Details details;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Details{
        private String phoneNumber;
        private Long amount;
    }
    public AirtimeVTURequest(AirtimeRequestDto airtimeRequestDto){
        this.requestId = getRandomNumberAsString();
        this.uniqueCode = getUniqueCodeFromInput(airtimeRequestDto.getBiller());
        this.details = new Details(airtimeRequestDto.getPhoneNumber(), airtimeRequestDto.getAmount());
    }

    private String getRandomNumberAsString(){
        SecureRandom random = new SecureRandom();
        int maxLimit = (int) Math.pow(10, 5) - 1; // Calculate the maximum limit for the specified digits
        return String.valueOf(random.nextInt(maxLimit + 1));
    }

    private String getUniqueCodeFromInput(Biller input){
        return switch(input){
            case MTN -> "MTN_24207";
            default -> throw new InvalidCredentialsException();
        };
    }
}
