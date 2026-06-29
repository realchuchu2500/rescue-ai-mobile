package com.rescue_ai.mobile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val decisionEngine = DecisionEngine()
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun updateVisionResults(results: List<DetectionResult>) {
        viewModelScope.launch {
            val currentAudio = _uiState.value
            val ievResult = decisionEngine.calculateIEV(results, currentAudio.audioLevel, currentAudio.audioDetection)
            _uiState.value = _uiState.value.copy(
                visionResults = results,
                ievScore = ievResult.score,
                ievLevel = ievResult.level
            )
        }
    }

    fun updateAudioResults(level: Float, detection: String) {
        viewModelScope.launch {
            val currentVision = _uiState.value.visionResults
            val ievResult = decisionEngine.calculateIEV(currentVision, level, detection)
            _uiState.value = _uiState.value.copy(
                audioLevel = level,
                audioDetection = detection,
                ievScore = ievResult.score,
                ievLevel = ievResult.level
            )
        }
    }

    data class UiState(
        val visionResults: List<DetectionResult> = emptyList(),
        val audioLevel: Float = 0f,
        val audioDetection: String = "Iniciando...",
        val ievScore: Float = 0f,
        val ievLevel: String = "ANALIZANDO..."
    )
}
