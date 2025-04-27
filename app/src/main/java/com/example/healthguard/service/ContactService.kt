package com.example.healthguard.service

import com.example.healthguard.data.contact.Contact

interface ContactService {
    suspend fun insertContact(contact: Contact)
    suspend fun updateContact(contact: Contact)
    suspend fun deleteContact(id: Int)
    suspend fun getContact(id: Int): Contact?
    suspend fun getAllContacts(): List<Contact>
}