package com.example.healthguard.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.healthguard.data.contact.Contact
import com.example.healthguard.data.contact.ContactDao
import com.example.healthguard.data.message.Message
import com.example.healthguard.data.message.MessageDao
import com.example.healthguard.util.Constants

@Database(
    entities = [Contact::class, Message::class],
    version = 4,
    exportSchema = false
)
abstract class HealthGuardDatabase: RoomDatabase() {
    abstract fun contactDao(): ContactDao
    abstract fun messageDao(): MessageDao

    companion object {
        @Volatile
        private var instance: HealthGuardDatabase? = null

        fun getDatabase(context: Context): HealthGuardDatabase {
            return instance ?: synchronized(this) {
                val newInstance = Room.databaseBuilder(
                    context.applicationContext,
                    HealthGuardDatabase::class.java,
                    Constants.DATABASE_NAME
                )
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            db.execSQL(
                                """
                                INSERT INTO messages (id, messageText)
                                VALUES (1, 'SOS! I need help! I''m at {{location}}')
                                """.trimIndent()
                            )
                        }
                    })
                    .build()
                instance = newInstance
                return newInstance
            }
        }
    }
}