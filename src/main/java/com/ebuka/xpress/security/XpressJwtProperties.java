package com.ebuka.xpress.security;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "application.security.jwt")
@Data
public class XpressJwtProperties {
    @Value("${application.jwt.security.secretKey}")
    private String secretKey;
    @Value("${application.jwt.security.expiration}")
    private long expiration;
    @Value("${application.jwt.security.refresh-token}")
    private long refreshTokenExpiration;
}
