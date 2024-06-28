package com.example.flatmatefinder.di

import com.example.flatmatefinder.Utils.Constants.BASE_URL
import com.example.flatmatefinder.api.API
import com.example.flatmatefinder.api.AuthInterceptor
import com.example.flatmatefinder.api.MainAPI
import com.example.flatmatefinder.api.OnboardingAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.Retrofit.Builder
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun providesRetrofitBuilder(): Retrofit.Builder {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
    }

    @Singleton
    @Provides
    fun providesOkHTTPClient(authInterceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(authInterceptor).build()
    }

    @Singleton
    @Provides
    fun providesOnboardingAPI(
        retrofitBuilder: Retrofit.Builder,
        okHttpClient: OkHttpClient
    ): OnboardingAPI {
        return retrofitBuilder.client(okHttpClient).build().create(OnboardingAPI::class.java)
    }

    @Singleton
    @Provides
    fun providesAPI(retrofitBuilder: Retrofit.Builder): API {
        return retrofitBuilder.build().create(API::class.java)
    }

    @Singleton
    @Provides
    fun provideMainAPI(retrofitBuilder: Retrofit.Builder, okHttpClient: OkHttpClient): MainAPI {
        return retrofitBuilder
            .client(okHttpClient)
            .build()
            .create(MainAPI::class.java)
    }


}