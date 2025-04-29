package com.example.healthguard.data.contact

import kotlinx.coroutines.flow.Flow

interface ContactsRepository {
    suspend fun insert(contact: Contact)
    suspend fun update(contact: Contact)
    suspend fun delete(contact: Contact)
    fun getContact(id: Int): Flow<Contact>
    fun getAllContacts(): Flow<List<Contact>>
    fun getAllMobileNumbers(): Flow<List<String>>
}