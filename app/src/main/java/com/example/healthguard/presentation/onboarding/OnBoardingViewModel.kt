package com.example.healthguard.presentation.onboarding

import androidx.lifecycle.ViewModel
import com.example.healthguard.service.OnBoardingService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.single
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val onBoardingService: OnBoardingService
) : ViewModel() {
    suspend fun onEvent(event: OnBoardingEvent) {
        when (event) {
            is OnBoardingEvent.SaveAppEntry -> {
                onBoardingService.setOnboardingCompleted(true);
            }
        }
    }

    suspend fun isOnboardingCompleted(): Boolean {
        return onBoardingService.isOnboardingCompleted().first()
    }
}