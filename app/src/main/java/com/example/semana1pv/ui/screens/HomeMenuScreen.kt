package com.example.semana1pv.ui.screens

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.semana1pv.device.DeviceSearchActivity
import com.example.semana1pv.util.NetworkUtils
import com.example.semana1pv.ui.theme.TextMuted

@Composable
fun HomeMenuScreen(
    onEscribir: () -> Unit,
    onHablar: () -> Unit,
    onLogout: () -> Unit
) {
    val context = LocalContext.current
    val online = NetworkUtils.isOnline(context)

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Home Menú", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.ExtraBold)
        Text(
            text = if (online) "Conexión a internet: OK" else "Sin internet (requerida por Firebase)",
            color = TextMuted
        )

        Spacer(Modifier.height(18.dp))

        Button(onClick = onEscribir, modifier = Modifier.fillMaxWidth().height(56.dp)) { Text("Escribir") }
        Spacer(Modifier.height(12.dp))
        Button(
            onClick = { context.startActivity(Intent(context, DeviceSearchActivity::class.java)) },
            modifier = Modifier.fillMaxWidth().height(56.dp)
        ) { Text("Buscar Dispositivo (Fragment + Geolocalización)") }

        Spacer(Modifier.height(24.dp))

        Text("Tip: agrega el Widget “Última frase” en tu pantalla de inicio.", color = TextMuted)

        Spacer(Modifier.weight(1f))

        Button(onClick = onLogout, modifier = Modifier.fillMaxWidth()) { Text("Cerrar sesión") }
    }
}
