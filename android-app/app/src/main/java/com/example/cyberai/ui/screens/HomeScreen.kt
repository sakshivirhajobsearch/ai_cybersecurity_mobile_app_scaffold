package com.example.cyberai.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cyberai.ui.navigation.Routes

@Composable
fun HomeScreen(navController: NavController) {
    Column(Modifier.padding(16.dp)) {
        Text("CyberAI — Home")
        Spacer(Modifier.height(12.dp))
        Button(onClick = { navController.navigate(Routes.UrlScanner) }) {
            Text("Go to URL Scanner")
        }
    }
}
