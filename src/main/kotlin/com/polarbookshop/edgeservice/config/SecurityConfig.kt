package com.polarbookshop.edgeservice.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.config.web.server.invoke
import org.springframework.security.oauth2.client.oidc.web.server.logout.OidcClientInitiatedServerLogoutSuccessHandler
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.authentication.HttpStatusServerEntryPoint
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers.pathMatchers

@Configuration(proxyBeanMethods = false)
@EnableWebFluxSecurity
class SecurityConfig {
    @Bean
    fun springSecurityFilterChain(
        http: ServerHttpSecurity,
        clientRegistrationRepository: ReactiveClientRegistrationRepository,
    ): SecurityWebFilterChain =
        http {
            authorizeExchange {
                authorize(pathMatchers("/", "/*.css", "/*.js", "/favicon.ico"), permitAll)
                authorize(anyExchange, authenticated)
            }
            exceptionHandling {
                authenticationEntryPoint = HttpStatusServerEntryPoint(HttpStatus.UNAUTHORIZED)
            }
            oauth2Login { }
            logout {
                logoutSuccessHandler = oidcLogoutSuccessHandler(clientRegistrationRepository)
            }
        }

    private fun oidcLogoutSuccessHandler(clientRegistrationRepository: ReactiveClientRegistrationRepository): ServerLogoutSuccessHandler {
        val oidcLogoutSuccessHandler =
            OidcClientInitiatedServerLogoutSuccessHandler(clientRegistrationRepository)
        oidcLogoutSuccessHandler.setPostLogoutRedirectUri("{baseUrl}")
        return oidcLogoutSuccessHandler
    }
}
