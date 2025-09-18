package com.polarbookshop.edgeservice

import org.springframework.boot.fromApplication
import org.springframework.boot.with

fun main(args: Array<String>) {
    fromApplication<EdgeServiceApplication>()
        .with(TestcontainersConfiguration::class)
        .run(*args)
}
