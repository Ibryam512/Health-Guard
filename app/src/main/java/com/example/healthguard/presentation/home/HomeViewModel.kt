package com.example.healthguard.presentation.home

import androidx.lifecycle.ViewModel
import com.example.healthguard.service.SOSService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val sosService: SOSService
) : ViewModel() {

    fun sendSOSMessage() {
        sosService.sendSOSMessage()
    }
}