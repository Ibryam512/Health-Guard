package com.example.healthguard.data.message

import kotlinx.coroutines.flow.Flow

interface MessagesRepository {
    suspend fun insert(message: Message)
    fun getLastMessage(): Flow<Message>
}