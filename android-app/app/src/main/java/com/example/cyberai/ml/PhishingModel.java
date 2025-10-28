package com.example.cyberai.ml;

public class PhishingModel {
	
	public static String predict(String url) {
		
		int score = 0;

		if (url.contains("verify") || url.contains("login") || url.contains("secure"))
			score++;
		if (url.contains("-") || url.contains("@"))
			score++;
		if (url.startsWith("http://"))
			score++;
		if (!url.contains(".com"))
			score++;

		return (score >= 2) ? "⚠️ Phishing risk" : "✅ Likely safe";
	}
}
