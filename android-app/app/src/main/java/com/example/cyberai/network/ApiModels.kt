package com.example.cyberai.network

data class AnalyzeRequest(val text: String)
data class AnalyzeResponse(val label: String, val score: Double)
