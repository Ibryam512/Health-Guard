package com.example.healthguard.presentation.onboarding

import androidx.annotation.DrawableRes
import com.example.healthguard.R

data class Page(
    val title: String,
    val description: String,
    @DrawableRes val image: Int,
)

val pages = listOf(
    Page(
        title = "Emergency SOS",
        description = "Send your location and alert contacts instantly with one tap.",
        image = R.drawable.ic_location
    ),
    Page(
        title = "Health Information",
        description = "Store your blood type, allergies, and medical data securely.",
        image = R.drawable.ic_health_information
    ),
    Page(
        title = "Chatbot Support",
        description = "Ask questions and get health tips from our integrated AI assistant.",
        image = R.drawable.ic_chatting
    )
)
