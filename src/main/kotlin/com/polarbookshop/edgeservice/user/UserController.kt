package com.polarbookshop.edgeservice.user

import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@RestController
@RequestMapping("user")
class UserController {
    @GetMapping
    fun getUser(
        @AuthenticationPrincipal oidcUser: OidcUser,
    ): Mono<User> =
        User(
            username = oidcUser.preferredUsername,
            firstName = oidcUser.givenName,
            lastName = oidcUser.familyName,
            roles = listOf("employee", "customer"),
        ).toMono()
}
