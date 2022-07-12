package com.example.i_go.di

import android.app.Application
import androidx.room.Room
import com.example.i_go.feature_note.data.data_source.PatientDatabase
import com.example.i_go.feature_note.data.repository.PatientRepositoryImpl
import com.example.i_go.feature_note.domain.repository.PatientRepository
import com.example.i_go.feature_note.domain.use_case.*
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
    fun providePatientDatabase(app: Application): PatientDatabase {
        return Room.databaseBuilder(
            app,
            PatientDatabase::class.java,
            PatientDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun providePatientRepository(db: PatientDatabase): PatientRepository {
        return PatientRepositoryImpl(db.patientDao)
    }

    @Provides
    @Singleton
    fun providePatientUseCases(repository: PatientRepository): PatientUseCases {
        return PatientUseCases(
            getPatients = GetPatients(repository),
            deletePatient = DeletePatient(repository),
            addPatient = AddPatient(repository),
            getPatient = GetPatient(repository)
        )
    }
}