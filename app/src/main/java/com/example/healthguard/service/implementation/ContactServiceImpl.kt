package com.example.healthguard.service.implementation

import com.example.healthguard.data.contact.Contact
import com.example.healthguard.service.ContactService

class ContactServiceImpl : ContactService {
    init {

    }

    override suspend fun insertContact(name: String, mobileNumber: String) {
        // Implementation for inserting a contact
    }

    override suspend fun updateContact(id: Int, name: String, mobileNumber: String) {
        // Implementation for updating a contact
    }

    override suspend fun deleteContact(id: Int) {
        // Implementation for deleting a contact
    }

    override suspend fun getContact(id: Int): Contact? {
        // Implementation for getting a contact by ID
        return null
    }

    override suspend fun getAllContacts(): List<Contact> {
        // Implementation for getting all contacts
        return emptyList()
    }
}