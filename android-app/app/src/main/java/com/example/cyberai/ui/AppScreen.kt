package com.example.cyberai.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.cyberai.ml.Inference
import com.example.cyberai.network.AnalyzeRequest
import com.example.cyberai.network.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun AppScreen() {
	
	var input by remember { mutableStateOf(TextFieldValue("")) }
	var result by remember { mutableStateOf("—") }
	var loading by remember { mutableStateOf(false) }
	Column(modifier = Modifier.padding(16.dp)) {
		Text("CyberAI — On-device & Server Inference", style = MaterialTheme.typography.titleLarge)
		Spacer(Modifier.height(12.dp))
		OutlinedTextField(
			value = input,
			onValueChange = { input = it },
			label = { Text("Paste log line / URL / text") },
			modifier = Modifier.fillMaxWidth()
		)
		Spacer(Modifier.height(12.dp))
		Row {
			Button(onClick = { result = Inference.classify(input.text) }) { Text("On-device Analyze") }
			Spacer(modifier = Modifier.width(8.dp))
			Button(
				onClick = {
					loading = true
					CoroutineScope(Dispatchers.IO).launch {
						try {
							val resp = RetrofitClient.api.analyze(AnalyzeRequest(input.text))
							result = "Server: ${resp.label} (score: ${resp.score})"
						} catch (e: Exception) {
							result = "Server error: ${e.message}"
						} finally {
							loading = false
						}
					}
				}
			) { Text("Server Analyze") }
		}
		Spacer(Modifier.height(16.dp))
		if (loading) Text("Loading…")
		Text("Result: $result")
	}
}
