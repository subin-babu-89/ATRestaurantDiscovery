package com.example.alltrailsatlunch.network

import com.example.alltrailsatlunch.model.PlacesResponse
import com.example.alltrailsatlunch.util.BASE_URL
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Network interface for the all trails lunch app. Makes network calls using the retrofit networking library
 */
interface ApiService {

    companion object {
        fun create(): ApiService {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
            val okHttpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

            val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

            val retrofit = Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create(moshi)).build()

            return retrofit.create(ApiService::class.java)
        }
    }

    @GET("nearbysearch/json")
    suspend fun getNearbyResult(
        @Query("location") locationString: String,
        @Query("radius") radius: Int = 1500,
        @Query("type") type: String = "restaurant",
        @Query("key") key: String,
        @Query("pagetoken") pageToken: String = ""
    ): PlacesResponse

    @GET("findplacefromtext/json?locationbias=circle:2000@47.6918452,-122.2226413&key=AIzaSyDIKzjfQQCahwJ9yEr8gBU9TqJ3MvbPXyY")
    suspend fun findPlaceFromText(
        @Query("input") inputString: String,
        @Query("inputtype") inputType: String = "textquery",
        @Query("locationbias") locationBias: String,
        @Query("key") key: String
    ): PlacesResponse
}