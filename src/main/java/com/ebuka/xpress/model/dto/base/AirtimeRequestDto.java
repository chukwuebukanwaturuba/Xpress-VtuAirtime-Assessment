package com.ebuka.xpress.model.dto.base;

import com.ebuka.xpress.model.enums.Biller;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AirtimeRequestDto {
    @NotBlank(message = "amount should not be blank")
    private Long amount;
    @Pattern(regexp = "\\d+",message = "digits are required only")
    @Size(min = 11, max = 11, message = "phone number should be exactly 11 digits")
    private String phoneNumber;

    private Biller biller;
}
