package com.example.healthguard.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.healthguard.data.contact.Contact
import com.example.healthguard.data.contact.ContactDao

@Database(
    entities = [Contact::class],
    version = 1,
    exportSchema = false
)
abstract class HealthGuardDatabase: RoomDatabase() {
    abstract fun contactDao(): ContactDao

    companion object {
        const val DATABASE_NAME = "health_guard_db"

        @Volatile
        private var instance: HealthGuardDatabase? = null

        fun getDatabase(context: Context): HealthGuardDatabase {
            return instance ?: synchronized(this) {
                val newInstance = Room.databaseBuilder(
                    context.applicationContext,
                    HealthGuardDatabase::class.java,
                    DATABASE_NAME
                ).build()
                instance = newInstance
                return newInstance
            }
        }
    }
}