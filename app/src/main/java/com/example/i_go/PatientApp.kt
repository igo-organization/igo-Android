package com.example.i_go

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.HiltAndroidApp

val Context.name_dataStore : DataStore<Preferences> by preferencesDataStore(name = "Doctor")
val Context.major_dataStore : DataStore<Preferences> by preferencesDataStore(name = "Major")
val Context.facility_dataStore : DataStore<Preferences> by preferencesDataStore(name = "Facility")

@HiltAndroidApp
class PatientApp : Application()