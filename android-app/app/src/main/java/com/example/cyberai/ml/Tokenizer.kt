package com.example.cyberai.ml

object Tokenizer {
	
	fun tokenize(text: String, maxLen: Int = 64): Pair<IntArray, IntArray> {
		val tokens = text.lowercase().take(maxLen)
		val ids = IntArray(maxLen)
		val mask = IntArray(maxLen)

		tokens.forEachIndexed { index, ch ->
			ids[index] = ch.code % 20000
			mask[index] = 1
		}

		return Pair(ids, mask)
	}
}
