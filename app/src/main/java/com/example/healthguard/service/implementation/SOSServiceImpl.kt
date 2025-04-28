package com.example.healthguard.service.implementation

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.telephony.SmsManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.healthguard.data.contact.ContactsRepository
import com.example.healthguard.service.SOSService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SOSServiceImpl(
    private val context: Context,
    private val contactsRepository: ContactsRepository
) : SOSService {
    @RequiresApi(Build.VERSION_CODES.S)
    private val smsManager = context.getSystemService<SmsManager>(SmsManager::class.java).createForSubscriptionId(1)

    @RequiresApi(Build.VERSION_CODES.S)
    override fun sendSOSMessage() {
        if (!permissionsGranted()) {
            Toast.makeText(context, "Grant all needed premissions to proceed", Toast.LENGTH_SHORT).show()
            return
        }

        CoroutineScope(Dispatchers.IO).launch {
            val numbers = contactsRepository.getAllMobileNumbers().first()
            for (number in numbers) {
                sendSMS(number)
            }
        }

        Toast.makeText(context, "SOS signal sent!", Toast.LENGTH_SHORT).show()
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun sendSMS(phoneNumber: String) {
        smsManager.sendTextMessage(
            phoneNumber,
            null,
            "SOS! I need help!",
            null,
            null
        )
    }

    private fun permissionsGranted(): Boolean {
        return ContextCompat
            .checkSelfPermission(
                context,
                Manifest.permission.SEND_SMS
            ) == PackageManager.PERMISSION_GRANTED
    }
}
