package com.example.semana1pv.ui.screens

import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.semana1pv.ui.componentes.AppOutlinedTextField
import com.example.semana1pv.ui.componentes.LinkText
import com.example.semana1pv.ui.theme.BackgroundLigth
import com.example.semana1pv.ui.theme.BandGreen
import com.example.semana1pv.ui.theme.TextDark
import com.example.semana1pv.ui.theme.TextMuted
import com.example.semana1pv.util.Validators
import kotlinx.coroutines.launch

@Composable
fun RecuperarScreen(
    onBackToLogin: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }

    // ComboBox (Spinner): canal de recuperacion
    val canales = listOf("Email", "SMS (simulado)", "WhatsApp (simulado)")
    var canalSeleccionado by remember { mutableStateOf(canales.first()) }

    // Radio: tipo de ayuda
    val tipos = listOf("Enviar enlace", "Enviar codigo")
    var tipoSeleccionado by remember { mutableStateOf(tipos.first()) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Surface(modifier = Modifier.fillMaxSize(), color = BackgroundLigth) {
        Scaffold(
            containerColor = BackgroundLigth,
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
                    .padding(18.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Recuperar contrasena",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.ExtraBold,
                    color = TextDark
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    text = "Pantalla simulada: muestra los componentes UI solicitados.",
                    color = TextMuted
                )

                Spacer(Modifier.height(18.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        AppOutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
                            label = "Correo de la cuenta",
                            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = BandGreen) }
                        )

                        Spacer(Modifier.height(12.dp))

                        Text(
                            text = "Canal (ComboBox)",
                            fontWeight = FontWeight.Bold,
                            color = TextMuted
                        )
                        Spacer(Modifier.height(8.dp))

                        ComboBoxSpinner(
                            items = canales,
                            selectedItem = canalSeleccionado,
                            onItemSelected = { canalSeleccionado = it },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(Modifier.height(12.dp))

                        Text(
                            text = "Tipo (Radio buttons)",
                            fontWeight = FontWeight.Bold,
                            color = TextMuted
                        )
                        Spacer(Modifier.height(8.dp))

                        tipos.forEach { t ->
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                RadioButton(
                                    selected = tipoSeleccionado == t,
                                    onClick = { tipoSeleccionado = t }
                                )
                                Text(text = t)
                            }
                        }

                        Spacer(Modifier.height(16.dp))

                        Button(
                            onClick = {
                                scope.launch {
                                    if (email.isBlank()) {
                                        snackbarHostState.showSnackbar("Ingresa un correo")
                                    } else {
                                        if (!Validators.isValidEmail(email)) {
                                            snackbarHostState.showSnackbar("Correo invalido")
                                            return@launch
                                        }

                                        snackbarHostState.showSnackbar(
                                            "Solicitud enviada por $canalSeleccionado - $tipoSeleccionado (simulado)"
                                        )
                                    }
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(54.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = BandGreen)
                        ) {
                            Text("Enviar", fontWeight = FontWeight.Bold, color = Color.White)
                        }

                        Spacer(Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(text = "Volver a ", color = TextMuted)
                            LinkText(text = "Login", onClick = onBackToLogin)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ComboBoxSpinner(
    items: List<String>,
    selectedItem: String,
    onItemSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val currentItems = rememberUpdatedState(items)
    val currentSelected = rememberUpdatedState(selectedItem)
    val currentOnItemSelected = rememberUpdatedState(onItemSelected)

    AndroidView(
        modifier = modifier,
        factory = { ctx ->
            Spinner(ctx).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )

                adapter = ArrayAdapter(
                    ctx,
                    android.R.layout.simple_spinner_item,
                    currentItems.value
                ).also { a ->
                    a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                }

                val initialIndex = currentItems.value.indexOf(currentSelected.value).coerceAtLeast(0)
                setSelection(initialIndex, false)

                onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: android.view.View?,
                        position: Int,
                        id: Long
                    ) {
                        val item = currentItems.value.getOrNull(position) ?: return
                        if (item != currentSelected.value) {
                            currentOnItemSelected.value(item)
                        }
                    }

                    override fun onNothingSelected(parent: AdapterView<*>) = Unit
                }
            }
        },
        update = { spinner ->
            val idx = currentItems.value.indexOf(currentSelected.value).coerceAtLeast(0)
            if (spinner.selectedItemPosition != idx) {
                spinner.setSelection(idx, false)
            }
        }
    )
}
