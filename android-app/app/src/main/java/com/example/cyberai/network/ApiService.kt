package com.example.cyberai.network

import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/analyze")
    suspend fun analyze(@Body body: AnalyzeRequest): AnalyzeResponse
}
