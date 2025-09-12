package com.example.nutritive_app.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.sns.SnsClient

@Configuration
class AwsConfig {

    @Bean
    fun snsClient(): SnsClient {
        return SnsClient.builder()
            .region(Region.EU_NORTH_1)
            .credentialsProvider(DefaultCredentialsProvider.create())
            .build()
    }
}
