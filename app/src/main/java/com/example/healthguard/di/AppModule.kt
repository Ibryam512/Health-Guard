package com.example.healthguard.di

import android.app.Application
import com.example.healthguard.data.HealthGuardDatabase
import com.example.healthguard.data.contact.ContactsRepository
import com.example.healthguard.data.contact.ContactsRepositoryImpl
import com.example.healthguard.service.OnBoardingService
import com.example.healthguard.service.implementation.OnBoardingServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideOnBoardingService(
        application: Application
    ): OnBoardingService = OnBoardingServiceImpl(context = application)

    @Provides
    @Singleton
    fun provideContactsRepository(
        application: Application
    ): ContactsRepository {
        val database = HealthGuardDatabase.getDatabase(application)
        return ContactsRepositoryImpl(database.contactDao())
    }
}