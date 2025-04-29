package com.example.healthguard.data.message

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class Message(
    @PrimaryKey
    val id: Int,
    val messageText: String
)
