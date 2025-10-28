package com.example.cyberai.ui.screens

object UrlScanner {
    fun analyze(url: String): Pair<String, Double> {
        val suspicious = listOf("phish", "login-verify", "update-password")
        val hit = suspicious.any { url.contains(it, ignoreCase = true) }
        return if (hit) "Suspicious" to 0.85 else "Clean" to 0.12
    }
}
