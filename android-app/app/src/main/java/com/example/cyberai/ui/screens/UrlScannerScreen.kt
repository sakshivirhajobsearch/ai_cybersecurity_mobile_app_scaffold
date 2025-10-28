package com.example.cyberai.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun UrlScannerScreen(navController: NavController) {
    var url by remember { mutableStateOf(TextFieldValue("")) }
    var result by remember { mutableStateOf("—") }

    Column(Modifier.padding(16.dp)) {
        Text("URL Scanner")
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            value = url,
            onValueChange = { url = it },
            label = { Text("Enter URL") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))
        Button(onClick = {
            val (label, score) = UrlScanner.analyze(url.text)
            result = "$label (score=${"%.2f".format(score)})"
        }) {
            Text("Analyze")
        }
        Spacer(Modifier.height(16.dp))
        Text("Result: $result")
    }
}
