package com.pabji.taproom.data.network.retrofit

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BeersApiClient(baseUrl: String) {

    val okHttpClient = OkHttpClient.Builder().build()

    val service = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build().create(BeersApiService::class.java)
}