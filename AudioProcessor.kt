package com.rescue_ai.mobile

import android.annotation.SuppressLint
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import kotlin.math.log10
import kotlin.math.sqrt

class AudioProcessor(private val onAudioLevel: (Float, String) -> Unit) {
    private var isRecording = false
    private val sampleRate = 44100
    private val channelConfig = AudioFormat.CHANNEL_IN_MONO
    private val audioFormat = AudioFormat.ENCODING_PCM_16BIT
    private val bufferSize = AudioRecord.getMinBufferSize(sampleRate, channelConfig, audioFormat)

    @SuppressLint("MissingPermission")
    fun start() {
        isRecording = true
        val audioRecord = AudioRecord(
            MediaRecorder.AudioSource.MIC,
            sampleRate,
            channelConfig,
            audioFormat,
            bufferSize
        )
        audioRecord.startRecording()
        Thread {
            val buffer = ShortArray(bufferSize)
            while (isRecording) {
                val read = audioRecord.read(buffer, 0, bufferSize)
                if (read > 0) {
                    val rms = calculateRMS(buffer, read)
                    val db = 20 * log10(rms.toDouble()).toFloat()
                    val detection = detectSoundPatterns(buffer, read)
                    onAudioLevel(db, detection)
                }
            }
            audioRecord.stop()
            audioRecord.release()
        }.start()
    }

    fun stop() {
        isRecording = false
    }

    private fun calculateRMS(buffer: ShortArray, read: Int): Float {
        var sum = 0.0
        for (i in 0 until read) {
            sum += buffer[i] * buffer[i]
        }
        return sqrt(sum / read).toFloat()
    }

    private fun detectSoundPatterns(buffer: ShortArray, read: Int): String {
        val rms = calculateRMS(buffer, read)
        return when {
            rms > 2000 -> "Posible Voz/Grito"
            rms > 1000 -> "Golpes/Sonido repetitivo"
            rms > 500 -> "Respiración/Susurro"
            else -> "Ruido ambiente"
        }
    }
}
