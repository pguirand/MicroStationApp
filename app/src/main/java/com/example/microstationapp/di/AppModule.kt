package com.example.microstationapp.di

import android.content.Context
import androidx.room.Room
import com.example.microstationapp.common.ApiDetails
import com.example.microstationapp.data.room.AppDatabase
import com.example.microstationapp.data.room.MIGRATION_1_2
import com.example.microstationapp.data.room.MeterDao
import com.example.microstationapp.data.room.SalesDao
import com.example.microstationapp.data.remote.ApiCall
import com.example.microstationapp.data.repository.Repository
import com.example.microstationapp.data.repository.RepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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
    fun provideDatabase(@ApplicationContext context:Context) : AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "microstation_database"
        )
            .addMigrations(MIGRATION_1_2)
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpInstance() : OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitInstance(
        client: OkHttpClient
    ) : Retrofit {
        return Retrofit.Builder()
            .baseUrl(ApiDetails.BASE_URL)
            .addConverterFactory((GsonConverterFactory.create()))
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideAPI(
        retrofit: Retrofit
    ) : ApiCall {
        return retrofit.create(ApiCall::class.java)
    }

//    @Provides
//    @Singleton
//    fun provideRepository(
//        apiCall: ApiCall
//    ) : Repository {
//        return RepositoryImpl(apiCall)
//    }

    @Provides
    @Singleton
    fun provideMeterDao(database: AppDatabase):MeterDao {
        return database.meterDao()
    }

    @Provides
    @Singleton
    fun provideSalesDao(database: AppDatabase) : SalesDao {
        return database.salesDao()
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule{

    @Binds
    @Singleton
    abstract fun bindRepository(
        repositoryImpl: RepositoryImpl
    ) : Repository
}