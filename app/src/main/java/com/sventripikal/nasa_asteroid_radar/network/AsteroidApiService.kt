package com.sventripikal.nasa_asteroid_radar.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Call
import retrofit2.Retrofit


private const val BASE_URL = "https://api.nasa.gov/neo/rest/v1/feed?"
private val START_DATE = "start_date=2015-09-07"
private val END_DATE = "end_date=2015-09-08"
private val API_KEY = "api_key=DEMO_KEY"


val contentType = "application/json".toMediaType()
val kotlinxConverterFactory = Json.asConverterFactory(contentType)

// builds network requests via Retrofit
private val retrofit = Retrofit.Builder()
    .addConverterFactory(kotlinxConverterFactory)
    .baseUrl(BASE_URL)
    .build()


// defines get request functions for Api object
interface AsteroidApiService {

    // get all asteroids for the week
    fun getAsteroidsForTheWeek(): Call<String>
}


// object to build & expose retrofit service
object AsteroidApi {

    // returns object that implements network service requests
    val retrofitService: AsteroidApiService by lazy {
        retrofit.create(AsteroidApiService::class.java)
    }
}