package com.example.i_go.di

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.room.Room
import com.example.i_go.feature_note._constants.Const.RemoteConst.BASE_URL
import com.example.i_go.feature_note.data.data_source.PatientDatabase
import com.example.i_go.feature_note.data.remote.UserAPI
import com.example.i_go.feature_note.data.repository.PatientRepositoryImpl
import com.example.i_go.feature_note.data.repository.UserRepositoryImpl
import com.example.i_go.feature_note.data.storage.TokenStore
import com.example.i_go.feature_note.data.storage.dataStore
import com.example.i_go.feature_note.domain.repository.PatientRepository
import com.example.i_go.feature_note.domain.repository.UserRepository
import com.example.i_go.feature_note.domain.use_case.*
import com.example.i_go.feature_note.domain.util.log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideAuthInterceptor(
        @ApplicationContext appContext: Context,
    ): Interceptor = Interceptor { chain ->
        val newRequest = chain
            .request()
            .newBuilder()
            .addHeader(
                "Authorization",
                "Bearer ${
                    runBlocking {
                        appContext.dataStore.data.map {
                            it[stringPreferencesKey("user")] }.first()
                    }
                }"
            )
            .build()
        return@Interceptor chain.proceed(newRequest)
    }
    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor() { it.log() }.setLevel(HttpLoggingInterceptor.Level.BODY)

    // client 제공
    @Provides
    @Singleton
    fun provideOkHttpClient(
        interceptor: Interceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    // Retrofit 제공
    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun providePatientDatabase(app: Application): PatientDatabase {
        return Room.databaseBuilder(
            app,
            PatientDatabase::class.java,
            PatientDatabase.DATABASE_NAME
        ).build()
    }

    // user api 제공
    @Provides
    @Singleton
    fun provideUserApi(retrofit: Retrofit): UserAPI {
        return retrofit.create(UserAPI::class.java)
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
    // recruitings repository 제공
    @Provides
    @Singleton
    fun provideUserRepository(store: TokenStore, api: UserAPI): UserRepository {
        return UserRepositoryImpl(store, api)
    }
}