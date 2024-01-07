package com.ebuka.xpress.model.dto.user;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginRequest {

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
