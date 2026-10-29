package com.spendwise.report.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Automatically injects the trusted internal gateway key into outbound OpenFeign calls.
 */
@Configuration
public class FeignClientConfig implements RequestInterceptor {

    @Value("${fintrack.internal.gateway-key:${INTERNAL_GATEWAY_KEY:}}")
    private String internalGatewayKey;

    @Override
    public void apply(RequestTemplate template) {
        if (internalGatewayKey != null && !internalGatewayKey.isBlank()) {
            template.header("X-Internal-Gateway-Key", internalGatewayKey.trim());
        }
    }
}

