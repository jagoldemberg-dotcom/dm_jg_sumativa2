package com.example.semana1pv.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.semana1pv.util.NetworkUtils
import com.example.semana1pv.ui.theme.BackgroundLigth
import com.example.semana1pv.ui.theme.BandGreen
import com.example.semana1pv.ui.theme.TextDark
import com.example.semana1pv.ui.theme.TextMuted

@Composable
fun HomeScreen(
    onLogout: () -> Unit = {}
) {
    val context = LocalContext.current
    val online = NetworkUtils.isOnline(context)

    val quickActions = listOf(
        "Escribir mensaje (mock)",
        "Leer mensaje (mock)",
        "Configurar accesibilidad (mock)",
        "Ayuda (mock)"
    )

    Surface(modifier = Modifier.fillMaxSize(), color = BackgroundLigth) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(18.dp)
        ) {
            Text(
                text = "Inicio",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.ExtraBold,
                color = TextDark
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = if (online) "Conexion: OK" else "Conexion: Sin internet (requerida)",
                color = if (online) BandGreen else TextMuted
            )

            Spacer(Modifier.height(16.dp))

            Text(
                text = "Acciones rapidas (Grilla)",
                fontWeight = FontWeight.Bold,
                color = TextDark
            )

            Spacer(Modifier.height(8.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                items(quickActions) { t ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(text = t, fontWeight = FontWeight.Bold)
                            Text(
                                text = "Boton grande para accesibilidad.",
                                color = TextMuted,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = onLogout,
                    colors = ButtonDefaults.buttonColors(containerColor = BandGreen)
                ) {
                    Text("Cerrar sesion", color = MaterialTheme.colorScheme.onPrimary)
                }
            }
        }
    }
}
