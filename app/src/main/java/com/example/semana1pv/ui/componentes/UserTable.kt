package com.example.semana1pv.ui.componentes

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.semana1pv.data.User
import com.example.semana1pv.ui.theme.BordernSoft

@Composable
fun UserTable(users: List<User>, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth().border(1.dp, BordernSoft)) {
        TableRow(
            c1 = "Nombre",
            c2 = "Correo",
            c3 = "Region",
            header = true
        )
        users.forEach { u ->
            TableRow(
                c1 = u.nombreCompleto,
                c2 = u.email,
                c3 = u.region,
                header = false
            )
        }
    }
}

@Composable
private fun TableRow(c1: String, c2: String, c3: String, header: Boolean) {
    val bg = if (header) MaterialTheme.colorScheme.surfaceVariant else Color.Transparent
    val fw = if (header) FontWeight.Bold else FontWeight.Normal

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(bg)
            .border(1.dp, BordernSoft)
            .padding(10.dp)
    ) {
        Text(text = c1, modifier = Modifier.weight(1.2f), fontWeight = fw)
        Text(text = c2, modifier = Modifier.weight(1.4f), fontWeight = fw)
        Text(text = c3, modifier = Modifier.weight(0.9f), fontWeight = fw)
    }
}
