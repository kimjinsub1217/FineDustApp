package com.example.toyproject018_finedust.data.services

import com.example.toyproject018_finedust.BuildConfig
import com.example.toyproject018_finedust.data.models.airquality.AirQualityResponse
import com.example.toyproject018_finedust.data.models.monitoringstation.MonitoringStations
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AirKoreaApiService {

    @GET(
        "B552584/MsrstnInfoInqireSvc/getNearbyMsrstnList" +
                "?serviceKey=${BuildConfig.AIR_KOREA_SERVICE_KEY}" +
                "&returnType=json"
    )
    suspend fun getNearbyMonitoringStation(
        @Query("tmX") tmX: Double,
        @Query("tmY") tmY: Double
    ): Response<MonitoringStations>

    @GET(
        "B552584/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty" +
                "?serviceKey=${BuildConfig.AIR_KOREA_SERVICE_KEY}" +
                "&returnType=json" +
                "&dataTerm=DAILY" +
                "&ver=1.3"
    )
    suspend fun getRealtimeAirQualties(
        @Query("stationName") StationName: String
    ): Response<AirQualityResponse>
}