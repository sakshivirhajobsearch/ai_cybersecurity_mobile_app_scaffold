package com.example.cyberai.ml

import android.content.Context

import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
import java.nio.ByteBuffer
import java.nio.ByteOrder

object Inference {
	private var interpreter: Interpreter? = null
	fun init(context: Context) {
		try {
			val fd = context.assets.openFd("cyberai.tflite")
			FileInputStream(fd.fileDescriptor).use { fis ->
				val buffer = fis.channel.map(FileChannel.MapMode.READ_ONLY, fd.startOffset, fd.declaredLength)
				interpreter = Interpreter(buffer)
			}
		} catch (e: Exception) {
			e.printStackTrace(); interpreter = null
		}
	}

	fun classify(text: String): String {
		val interp = interpreter ?: return "Model not initialized"
		return try {
			val input = ByteBuffer.allocateDirect(4).order(ByteOrder.nativeOrder()).putFloat(text.length.toFloat())
				.apply { flip() }
			val output = FloatArray(1)
			interp.run(input, output)
			if (output[0] > 0.5f) "⚠️ Suspicious" else "✅ Normal"
		} catch (e: Exception) {
			"Inference error: ${e.message}"
		}
	}
}
