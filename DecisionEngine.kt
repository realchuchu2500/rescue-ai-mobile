package com.rescue_ai.mobile

class DecisionEngine {
    fun calculateIEV(
        visionResults: List<DetectionResult>,
        audioDb: Float,
        audioDetection: String
    ): LifeEvidenceResult {
        var score = 0f
        val personDetected = visionResults.any { it.label == "person" }
        if (personDetected) {
            score += 0.6f
        } else if (visionResults.isNotEmpty()) {
            score += 0.2f
        }
        if (audioDetection.contains("Voz") || audioDetection.contains("Grito")) {
            score += 0.4f
        } else if (audioDetection.contains("Golpes")) {
            score += 0.3f
        } else if (audioDetection.contains("Respiración")) {
            score += 0.2f
        }
        score = score.coerceIn(0f, 1f)
        val level = when {
            score > 0.7f -> "ZONA PRIORITARIA PARA INSPECCIÓN"
            score > 0.4f -> "EVIDENCIA MEDIA"
            else -> "EVIDENCIA BAJA"
        }
        return LifeEvidenceResult(score, level)
    }

    data class LifeEvidenceResult(
        val score: Float,
        val level: String
    )
}

data class DetectionResult(
    val label: String,
    val confidence: Float,
    val location: FloatArray
)
