package com.example.i_go.feature_note.data.data_source

import androidx.room.*
import com.example.i_go.feature_note.domain.model.Patient
import kotlinx.coroutines.flow.Flow

@Dao
interface PatientDao {
    @Query("SELECT * FROM patient")
    fun getPatients(): Flow<List<Patient>>

    @Query("SELECT * FROM patient WHERE id = :id")
    suspend fun getPatientById(id: Int): Patient?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPatient(patient: Patient)

    @Delete
    suspend fun deletePatient(patient: Patient)
}