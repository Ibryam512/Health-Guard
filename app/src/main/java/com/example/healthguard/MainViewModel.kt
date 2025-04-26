package com.example.healthguard

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthguard.service.OnBoardingService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val onBoardingService: OnBoardingService
): ViewModel() {
    var showNavBar by mutableStateOf(false)
        private set

    var splashCondition by mutableStateOf(true)
        private set

    init {
        onBoardingService.isOnboardingCompleted().onEach { completed ->
            showNavBar = completed
            delay(100)
            splashCondition = false
        }.launchIn(viewModelScope)
    }
}