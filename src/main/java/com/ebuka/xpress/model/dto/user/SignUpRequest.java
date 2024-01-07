package com.ebuka.xpress.model.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@RequiredArgsConstructor
@AllArgsConstructor

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SignUpRequest {
    @JsonProperty("first_name")
    @NotEmpty(message = "first name cannot be empty")
    private String firstName;

    @JsonProperty("last_name")
    @NotEmpty(message = "last name cannot be empty")
    private String lastName;

    @JsonProperty("phone_number")
    @NotEmpty(message = "phone number cannot be empty")
    private String phoneNumber;

    @JsonProperty("email_address")
    @NotEmpty(message = "email address cannot be empty")
    @Email(message = "invalid email address")
    private String emailAddress;

    @JsonProperty("password")
    @NotEmpty(message = "password cannot be empty")
    @Size(min = 8, message = "password must be at least 8 characters long")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
            message = "password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character"
    )
    private String password;
}
