package com.example.healthguard.service

import com.example.healthguard.data.message.Message

interface MessageService {
    suspend fun getMessage(): Message
    suspend fun insertMessage(message: Message)
}