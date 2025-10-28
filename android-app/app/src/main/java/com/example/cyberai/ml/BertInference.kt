package com.example.cyberai.ml

import android.content.Context
import org.tensorflow.lite.Interpreter
import java.nio.ByteBuffer
import java.nio.ByteOrder

class BertInference(context: Context) {

	private val interpreter: Interpreter

	init {
		val modelFile = context.assets.open("cyber_bert.tflite").readBytes()
		val bb = ByteBuffer.allocateDirect(modelFile.size).order(ByteOrder.nativeOrder())
		bb.put(modelFile)
		interpreter = Interpreter(bb)
	}

	fun predict(inputIds: IntArray, mask: IntArray): Float {
		val inputIdsBuffer = ByteBuffer.allocateDirect(4 * inputIds.size).order(ByteOrder.nativeOrder())
		val maskBuffer = ByteBuffer.allocateDirect(4 * mask.size).order(ByteOrder.nativeOrder())

		inputIds.forEach { inputIdsBuffer.putInt(it) }
		mask.forEach { maskBuffer.putInt(it) }

		val output = Array(1) { FloatArray(2) }
		interpreter.runForMultipleInputsOutputs(
			arrayOf(inputIdsBuffer, maskBuffer),
			mapOf(0 to output)
		)

		return output[0][1] // threat probability
	}
}
