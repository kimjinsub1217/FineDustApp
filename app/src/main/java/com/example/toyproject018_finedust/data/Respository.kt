package com.example.toyproject018_finedust.data

import android.os.Build
import com.example.toyproject018_finedust.BuildConfig
import com.example.toyproject018_finedust.data.models.airquality.MeasuredValue
import com.example.toyproject018_finedust.data.models.monitoringstation.MonitoringStation
import com.example.toyproject018_finedust.data.services.AirKoreaApiService
import com.example.toyproject018_finedust.data.services.KakaoLocalApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object Respository {

    suspend fun getNearbyMonitoringStation(latitude: Double, longitude: Double):MonitoringStation? {
        val tmCoordinates = kaKaoLocalApiService
            .getTmCoordinates(longitude, latitude)
            .body()
            ?.documents
            ?.firstOrNull()

        val tmX = tmCoordinates?.x
        val tmY = tmCoordinates?.y

        return airKoreaApiService
            .getNearbyMonitoringStation(tmX!!,tmY!!)
            .body()
            ?.response
            ?.body
            ?.monitoringStations
            ?.minByOrNull { it.tm ?: Double.MAX_VALUE }
    }

    suspend fun getLatestAirQualityData(stationName: String):MeasuredValue?=
        airKoreaApiService
            .getRealtimeAirQualties(stationName)
            .body()
            ?.response
            ?.body
            ?.measuredValues
            ?.firstOrNull()


    private val kaKaoLocalApiService: KakaoLocalApiService by lazy {
        Retrofit.Builder()
            .baseUrl(Url.KAKAO_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(buildHttpClient())
            .build()
            .create()
    }

    private val airKoreaApiService: AirKoreaApiService by lazy {
        Retrofit.Builder()
            .baseUrl(Url.AIR_KOREA_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(buildHttpClient())
            .build()
            .create()
    }

    private fun buildHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = if (BuildConfig.DEBUG) {
                        HttpLoggingInterceptor.Level.BODY
                    } else {
                        HttpLoggingInterceptor.Level.NONE
                    }
                }
            )
            .build()
}