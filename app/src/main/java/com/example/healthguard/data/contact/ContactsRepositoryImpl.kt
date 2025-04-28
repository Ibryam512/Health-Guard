package com.example.healthguard.data.contact

class ContactsRepositoryImpl(private val contactDao: ContactDao) : ContactsRepository {
    override suspend fun insert(contact: Contact) {
        contactDao.insert(contact)
    }

    override suspend fun update(contact: Contact) {
        contactDao.update(contact)
    }

    override suspend fun delete(contact: Contact) {
        contactDao.delete(contact)
    }

    override fun getItem(id: Int) = contactDao.getItem(id)

    override fun getAllItems() = contactDao.getAllItems()

    override fun getAllMobileNumbers() = contactDao.getAllMobileNumbers()
}