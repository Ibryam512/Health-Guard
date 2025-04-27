package com.example.healthguard.service.implementation

import com.example.healthguard.data.contact.Contact
import com.example.healthguard.data.contact.ContactsRepository
import com.example.healthguard.service.ContactService
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull

class ContactServiceImpl(private val contactsRepository: ContactsRepository) : ContactService {

    override suspend fun insertContact(contact: Contact) {
        contactsRepository.insert(contact)
    }

    override suspend fun updateContact(contact: Contact) {
        contactsRepository.update(contact)
    }

    override suspend fun deleteContact(id: Int) {
        val contact = contactsRepository.getItem(id).first()
        contact?.let {
            contactsRepository.delete(it)
        }
    }

    override suspend fun getContact(id: Int): Contact? {
        return contactsRepository.getItem(id).first()
            .takeIf { it.id == id }
    }

    override suspend fun getAllContacts(): List<Contact> {
        return contactsRepository.getAllItems()
            .firstOrNull() ?: emptyList()
    }
}