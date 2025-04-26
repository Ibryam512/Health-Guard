package com.example.healthguard.service

import com.example.healthguard.data.contact.Contact

interface ContactService {
    suspend fun insertContact(name: String, mobileNumber: String)
    suspend fun updateContact(id: Int, name: String, mobileNumber: String)
    suspend fun deleteContact(id: Int)
    suspend fun getContact(id: Int): Contact?
    suspend fun getAllContacts(): List<Contact>
}