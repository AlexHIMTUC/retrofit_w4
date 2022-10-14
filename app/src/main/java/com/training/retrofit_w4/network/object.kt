package com.training.retrofit_w4.network

import com.training.retrofit_w4.helper.Const.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

object AppModule{
    @Singleton
    @Provides
    fun getRetrofitServiceInstance(retrofit: Retrofit) : retrofitTemplate{
        return retrofit.create(retrofitTemplate::class.java)
    }
    @Singleton
    @Provides
    fun getRetrofitInstance() : Retrofit{
        return Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
    }
}