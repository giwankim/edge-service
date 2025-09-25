package com.polarbookshop.edgeservice.config

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.security.Principal

@Configuration
class RateLimiterConfig {
    @Bean
    fun keyResolver(): KeyResolver =
        KeyResolver { exchange ->
            exchange
                .getPrincipal<Principal>()
                .map(Principal::getName)
                .defaultIfEmpty("anonymous")
        }
}
