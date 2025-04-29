package com.example.healthguard.presentation.message

import com.example.healthguard.data.message.Message

data class MessageUiState(
    val message: Message? = null,
    val isEditMode: Boolean = false
)