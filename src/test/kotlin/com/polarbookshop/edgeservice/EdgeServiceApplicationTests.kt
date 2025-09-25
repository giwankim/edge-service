package com.polarbookshop.edgeservice

import com.ninjasquad.springmockk.MockkBean
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository

@Import(TestcontainersConfiguration::class)
@SpringBootTest
class EdgeServiceApplicationTests {
    @MockkBean lateinit var clientRegistrationRepository: ReactiveClientRegistrationRepository

    @Test
    fun contextLoads() {
    }
}
