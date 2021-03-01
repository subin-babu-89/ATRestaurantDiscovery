package com.example.alltrailsrestaurantdiscovery.network

import com.example.alltrailsrestaurantdiscovery.model.PlaceDetailsResults
import com.example.alltrailsrestaurantdiscovery.model.Resources
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface PeopleServiceAPI {

    companion object {
        const val BASE_URL = "https://maps.googleapis.com/maps/api/place/"

        fun create(): PeopleServiceAPI {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
            val okHttpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
            val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

            val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi)).client(okHttpClient)
                .build()
            return retrofit.create(PeopleServiceAPI::class.java)
        }
    }

    @GET("nearbysearch/json")
    suspend fun getPlacesResult(
        @Query("location") locationString: String,
        @Query("radius") radius: Int = 1500,
        @Query("type") type: String = "restaurant",
        @Query("key") key: String,
        @Query("pagetoken") pageToken: String = ""
    ): Resources

    @GET("details/json")
    suspend fun getPlaceDetails(
        @Query("place_id") placeId: String,
        @Query("key") key: String
    ): PlaceDetailsResults
}