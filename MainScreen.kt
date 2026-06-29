package com.rescue_ai.mobile

import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun MainScreen(viewModel: MainViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = { ctx ->
                PreviewView(ctx).apply {
                    scaleType = PreviewView.ScaleType.FILL_CENTER
                }
            },
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "RESCUE AI MOBILE",
                color = Color.Yellow,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .background(Color.Black.copy(alpha = 0.6f))
                    .padding(8.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color.Black.copy(alpha = 0.8f)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 200.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "ÍNDICE DE EVIDENCIA DE VIDA (IEV)",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = uiState.ievLevel,
                        color = if (uiState.ievScore > 0.7f) Color.Red else Color.Green,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    
                    Divider(modifier = Modifier.padding(vertical = 8.dp), color = Color.Gray)

                    Row(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("EVIDENCIA VISUAL", color = Color.LightGray, fontSize = 12.sp)
                            LazyColumn(modifier = Modifier.height(60.dp)) {
                                items(uiState.visionResults) { result ->
                                    Text("${result.label} (${(result.confidence * 100).toInt()}%)", color = Color.White)
                                }
                            }
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            Text("EVIDENCIA ACÚSTICA", color = Color.LightGray, fontSize = 12.sp)
                            Text(uiState.audioDetection, color = Color.White, fontWeight = FontWeight.Bold)
                            Text("Nivel: ${uiState.audioLevel.toInt()} dB", color = Color.White)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text(
                            text = "CREADO POR JESÚS LORENZO",
                            color = Color.Gray,
                            fontSize = 10.sp
                        )
                    }
                }
            }
        }
    }
}
