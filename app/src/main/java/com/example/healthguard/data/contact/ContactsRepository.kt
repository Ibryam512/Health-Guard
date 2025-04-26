package com.example.healthguard.data.contact

interface ContactsRepository {
    suspend fun insert(contact: Contact)
    suspend fun update(contact: Contact)
    suspend fun delete(contact: Contact)
    fun getItem(id: Int): kotlinx.coroutines.flow.Flow<Contact>
    fun getAllItems(): kotlinx.coroutines.flow.Flow<List<Contact>>
}