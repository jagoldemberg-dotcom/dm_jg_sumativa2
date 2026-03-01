package com.example.semana1pv.ui.screens

import android.app.Activity
import android.content.Intent
import android.speech.RecognizerIntent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.semana1pv.ui.theme.TextMuted
import com.example.semana1pv.ui.viewmodel.PhraseViewModel
import java.util.Locale

@Composable
fun HablarScreen(
    onBack: () -> Unit = {}
) {
    val vm: PhraseViewModel = viewModel()
    var recognized by remember { mutableStateOf("") }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { res ->
        if (res.resultCode == Activity.RESULT_OK) {
            val data = res.data
            val results = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            recognized = results?.firstOrNull().orEmpty()
            if (recognized.isNotBlank()) vm.save(recognized, "speak")
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Hablar", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.ExtraBold)
        Text("Presiona el botón para dictar. Se guardará como frase.", color = TextMuted)

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                    putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                    putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale("es", "CL"))
                    putExtra(RecognizerIntent.EXTRA_PROMPT, "Habla ahora…")
                }
                launcher.launch(intent)
            },
            modifier = Modifier.fillMaxWidth().height(52.dp)
        ) { Text("Iniciar dictado") }

        Spacer(Modifier.height(20.dp))

        Text("Texto reconocido:", fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))
        Text(if (recognized.isBlank()) "—" else recognized, style = MaterialTheme.typography.titleLarge)

        Spacer(Modifier.height(20.dp))

        Button(onClick = onBack, modifier = Modifier.fillMaxWidth()) { Text("Volver") }
    }
}
