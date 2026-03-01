package com.example.semana1pv.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.semana1pv.ui.theme.BandGreen
import com.example.semana1pv.ui.theme.TextMuted
import com.example.semana1pv.ui.viewmodel.PhraseViewModel
import com.example.semana1pv.util.TtsHelper

@Composable
fun EscribirScreen(
    onBack: () -> Unit = {}
) {
    val context = LocalContext.current
    val vm: PhraseViewModel = viewModel()
    val tts = remember { TtsHelper(context) }
    var text by remember { mutableStateOf("") }

    val latest by vm.latestLocal.collectAsState()

    LaunchedEffect(Unit) { vm.refreshLocal() }
    DisposableEffect(Unit) { onDispose { tts.shutdown() } }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Escribir", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.ExtraBold)
        Text("Escribe un mensaje y presiona “Hablar” para que el teléfono lo lea en voz alta.", color = TextMuted)

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            modifier = Modifier.fillMaxWidth().heightIn(min = 120.dp),
            label = { Text("Mensaje") },
            textStyle = MaterialTheme.typography.titleLarge
        )

        Spacer(Modifier.height(10.dp))

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Button(
                onClick = {
                    val msg = text.trim()
                    if (msg.isNotBlank()) {
                        vm.save(msg, "write")
                        tts.speak(msg)
                    }
                },
                modifier = Modifier.weight(1f)
            ) { Text("Hablar") }

            Button(
                onClick = { onBack() },
                modifier = Modifier.weight(1f)
            ) { Text("Volver") }
        }

        Spacer(Modifier.height(16.dp))

        Text("Historial (local / ContentProvider)", fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(6.dp))
        LazyColumn(modifier = Modifier.fillMaxWidth().weight(1f)) {
            items(latest) { p ->
                Text("• ${p.text}", style = MaterialTheme.typography.bodyLarge)
                Spacer(Modifier.height(6.dp))
            }
        }
    }
}
