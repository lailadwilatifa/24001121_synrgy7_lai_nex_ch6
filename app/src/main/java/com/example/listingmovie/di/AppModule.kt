package com.example.listingmovie.di

import android.content.Context
import com.example.listingmovie.common.Constants
import com.example.listingmovie.data.local.UserDataStoreManager
import com.example.listingmovie.data.remote.TmdbApi
import com.example.listingmovie.data.repository.TmdbRepositoryImpl
import com.example.listingmovie.domain.repository.TmdbRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideTmdbApi(): TmdbApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TmdbApi::class.java)
    }

    @Provides
    fun provideTmdbRepository(api: TmdbApi): TmdbRepository {
        return TmdbRepositoryImpl(api)
    }

    @Provides
    fun provideUserDataStoreManager(@ApplicationContext context: Context): UserDataStoreManager {
        return UserDataStoreManager(context)
    }
}