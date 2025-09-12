package com.example.nutritive_app.service

import com.example.nutritive_app.repository.UserRepository
import org.springframework.stereotype.Service
import software.amazon.awssdk.services.sns.SnsClient
import software.amazon.awssdk.services.sns.model.CreatePlatformEndpointRequest
import software.amazon.awssdk.services.sns.model.PublishRequest

@Service
class SnsService(
    private val snsClient: SnsClient,
    private val userRepository: UserRepository
    ) {

    private val platformApplicationArn = "arn:aws:sns:eu-north-1:123456789012:app/GCM/NutritiveApp"

    fun registerDeviceToken(token: String, userId: Long): String {
        val request = CreatePlatformEndpointRequest.builder()
            .platformApplicationArn(platformApplicationArn)
            .token(token)
            .build()

        val result = snsClient.createPlatformEndpoint(request)
        val endpointArn = result.endpointArn()

        val user = userRepository.findById(userId)
            .orElseThrow { RuntimeException("User not found") }

        user.snsEndpointArn = endpointArn
        userRepository.save(user)

        return endpointArn
    }

    fun sendAllergenAlert(endpointArn: String, productName: String, allergens: List<String>) {

        val message = """
        {
          "default": "Alert: $productName contains allergens: $allergens",
          "GCM": "{ \"notification\": { \"title\": \"Allergen Alert\", \"body\": \"$productName contains: $allergens\" } }"
        }
    """.trimIndent()

        snsClient.publish(
            PublishRequest.builder()
                .targetArn(endpointArn)
                .messageStructure("json")
                .message(message)
                .build()
        )
    }
}
