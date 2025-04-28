package com.example.healthguard.data.contact

import kotlinx.coroutines.flow.Flow

interface ContactsRepository {
    suspend fun insert(contact: Contact)
    suspend fun update(contact: Contact)
    suspend fun delete(contact: Contact)
    fun getItem(id: Int): Flow<Contact>
    fun getAllItems(): Flow<List<Contact>>
    fun getAllMobileNumbers(): Flow<List<String>>
}