package com.example.healthguard.service

import kotlinx.coroutines.flow.Flow

interface OnBoardingService {
    suspend fun setOnboardingCompleted(completed: Boolean)
    fun isOnboardingCompleted(): Flow<Boolean>
}