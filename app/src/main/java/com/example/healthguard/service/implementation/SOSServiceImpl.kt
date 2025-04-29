package com.example.healthguard.service.implementation

import android.Manifest
import android.app.Activity
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.telephony.SmsManager
import android.telephony.SubscriptionManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.healthguard.data.contact.ContactsRepository
import com.example.healthguard.service.SOSService
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume

class SOSServiceImpl(
    private val context: Context,
    private val contactsRepository: ContactsRepository
) : SOSService {
    @RequiresApi(Build.VERSION_CODES.S)
    private val smsManager = getActiveSmsManager()

    @RequiresApi(Build.VERSION_CODES.S)
    override fun sendSOSMessage() {
        if (!permissionsGranted()) {
            Toast.makeText(context, "Grant all needed permissions to proceed", Toast.LENGTH_SHORT).show()
            return
        }

        CoroutineScope(Dispatchers.IO).launch {
            val numbers = contactsRepository.getAllMobileNumbers().first()

            if (numbers.isEmpty()) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "There are no saved contacts!", Toast.LENGTH_SHORT).show()
                }
                return@launch
            }

            val location = getLocation()

            if (location == null) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Location did not retrieve. Please, turn on your GPS.", Toast.LENGTH_SHORT).show()
                    }
            }

            val googleMapsLink = getGoogleMapsLink(location = location)

            for (number in numbers) {
                sendSMS(number, googleMapsLink)
            }

            withContext(Dispatchers.Main) {
                Toast.makeText(context, "SOS signal sent!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun sendSMS(phoneNumber: String, googleMapsLink: String) {
        val sentIntent = PendingIntent.getBroadcast(
            context, 0, Intent("SMS_SENT"), PendingIntent.FLAG_IMMUTABLE
        )

        smsManager.sendTextMessage(
            phoneNumber,
            null,
            "SOS! I need help! I am at $googleMapsLink",
            sentIntent,
            null
        )

        // BroadcastReceiver to handle SMS send status
        ContextCompat.registerReceiver(context, object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                when (resultCode) {
                    Activity.RESULT_OK -> {
                        Toast.makeText(context, "SMS sent successfully", Toast.LENGTH_SHORT).show()
                    }

                    SmsManager.RESULT_ERROR_GENERIC_FAILURE -> {
                        Toast.makeText(context, "Generic failure", Toast.LENGTH_SHORT).show()
                    }

                    SmsManager.RESULT_ERROR_NO_SERVICE -> {
                        Toast.makeText(context, "No service", Toast.LENGTH_SHORT).show()
                    }

                    SmsManager.RESULT_ERROR_NULL_PDU -> {
                        Toast.makeText(context, "Null PDU", Toast.LENGTH_SHORT).show()
                    }

                    SmsManager.RESULT_ERROR_RADIO_OFF -> {
                        Toast.makeText(context, "Radio off", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }, IntentFilter("SMS_SENT"), ContextCompat.RECEIVER_NOT_EXPORTED)
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun getActiveSmsManager(): SmsManager {
        val hasGrantedPermission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.READ_PHONE_STATE
        ) == PackageManager.PERMISSION_GRANTED

        val subscriptionManager = context.getSystemService(SubscriptionManager::class.java)
        val activeSubscriptionInfoList = subscriptionManager.activeSubscriptionInfoList

        val subscriptionId =
            if (hasGrantedPermission) activeSubscriptionInfoList?.firstOrNull()?.subscriptionId
                ?: SubscriptionManager.getDefaultSubscriptionId()
            else SubscriptionManager.getDefaultSubscriptionId()

        return context.getSystemService(SmsManager::class.java).createForSubscriptionId(subscriptionId)
    }

    private suspend fun getLocation(): Location? {
        val fusedLocationProviderClient: FusedLocationProviderClient
            = LocationServices.getFusedLocationProviderClient(context)

        val hasGrantedFineLocationPermission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val hasGrantedCoarseLocationPermission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val locationManager = context.getSystemService(
            Context.LOCATION_SERVICE
        ) as LocationManager

        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

        if (!isGpsEnabled) {
            return null
        }

        return suspendCancellableCoroutine { cont ->

            fusedLocationProviderClient.lastLocation.apply {
                if (isComplete) {
                    if (isSuccessful) {
                        cont.resume(result)
                    } else {
                        cont.resume(null)
                    }
                    return@suspendCancellableCoroutine
                }

                addOnSuccessListener {
                    cont.resume(result)
                }

                addOnFailureListener {
                    cont.resume(null)
                }

                addOnCanceledListener {
                    cont.cancel()
                }
            }
        }
    }

    private fun permissionsGranted(): Boolean {
        return ContextCompat
            .checkSelfPermission(
                context,
                Manifest.permission.SEND_SMS
            ) == PackageManager.PERMISSION_GRANTED
                && (ContextCompat
            .checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
                || ContextCompat
            .checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED)

    }

    private fun getGoogleMapsLink(location: Location?): String {
        return "https://www.google.com/maps/place/" + location?.latitude + "," + location?.longitude
    }
}
