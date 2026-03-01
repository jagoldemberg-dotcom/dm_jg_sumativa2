package com.example.semana1pv.ui.screens

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
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.semana1pv.data.User
import com.example.semana1pv.data.UserStore
import com.example.semana1pv.ui.componentes.AppOutlinedTextField
import com.example.semana1pv.ui.componentes.AppPasswordField
import com.example.semana1pv.ui.componentes.LinkText
import com.example.semana1pv.ui.componentes.UserGrid
import com.example.semana1pv.ui.componentes.UserTable
import com.example.semana1pv.ui.theme.BackgroundLigth
import com.example.semana1pv.ui.theme.BandGreen
import com.example.semana1pv.ui.theme.BordernSoft
import com.example.semana1pv.ui.theme.TextDark
import com.example.semana1pv.ui.theme.TextMuted
import com.example.semana1pv.ui.viewmodel.AuthState
import com.example.semana1pv.ui.viewmodel.AuthViewModel
import com.example.semana1pv.util.Validators
import com.example.semana1pv.util.onlyDigits
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroScreen(
    onLoginClick: () -> Unit = {}
) {
    var rut by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    var apellidoPaterno by remember { mutableStateOf("") }
    var apellidoMaterno by remember { mutableStateOf("") }
    var comuna by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    // ComboBox (Region)
    val regiones = listOf(
        "Arica y Parinacota",
        "Tarapaca",
        "Antofagasta",
        "Atacama",
        "Coquimbo",
        "Valparaiso",
        "Metropolitana",
        "O'Higgins",
        "Maule",
        "Nuble",
        "Biobio",
        "La Araucania",
        "Los Rios",
        "Los Lagos",
        "Aysen",
        "Magallanes"
    )
    var region by remember { mutableStateOf(regiones.first()) }
    var regionMenuExpanded by remember { mutableStateOf(false) }

    // Radio Buttons
    val modosLectura = listOf("Texto grande", "Alto contraste", "Normal")
    var modoLectura by remember { mutableStateOf(modosLectura.first()) }

    // Check list (ayudas)
    val ayudas = listOf(
        "Botones grandes",
        "Vibracion al confirmar (simulado)",
        "Lectura por voz (simulado)"
    )
    val ayudasSeleccionadas = remember { mutableStateListOf<String>() }

    val canSubmit =
        rut.isNotBlank() &&
                nombre.isNotBlank() &&
                apellidoPaterno.isNotBlank() &&
                apellidoMaterno.isNotBlank() &&
                comuna.isNotBlank() &&
                telefono.isNotBlank() &&
                email.isNotBlank() &&
                password.isNotBlank() &&
                confirmPassword.isNotBlank() &&
                password == confirmPassword &&
                ayudasSeleccionadas.isNotEmpty()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val authVm: AuthViewModel = viewModel()
    val authState by authVm.state.collectAsState()

    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Error -> snackbarHostState.showSnackbar((authState as AuthState.Error).message)
            else -> Unit
        }
    }

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
                    .padding(horizontal = 18.dp, vertical = 12.dp)
            ) {
                Text(
                    text = "Registro de Usuario",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.ExtraBold,
                    color = TextDark
                )
                Text(
                    text = "Maximo 5 usuarios (memoria) - actuales: ${UserStore.count()}/5",
                    color = TextMuted
                )

                Spacer(Modifier.height(14.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Datos personales",
                            fontWeight = FontWeight.Bold,
                            color = TextMuted
                        )
                        Spacer(Modifier.height(10.dp))

                        AppOutlinedTextField(
                            value = rut,
                            onValueChange = { rut = it },
                            label = "RUT",
                            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = BandGreen) }
                        )
                        Spacer(Modifier.height(10.dp))

                        AppOutlinedTextField(
                            value = nombre,
                            onValueChange = { nombre = it },
                            label = "Nombre",
                            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = BandGreen) }
                        )
                        Spacer(Modifier.height(10.dp))

                        AppOutlinedTextField(
                            value = apellidoPaterno,
                            onValueChange = { apellidoPaterno = it },
                            label = "Apellido paterno",
                            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = BandGreen) }
                        )
                        Spacer(Modifier.height(10.dp))

                        AppOutlinedTextField(
                            value = apellidoMaterno,
                            onValueChange = { apellidoMaterno = it },
                            label = "Apellido materno",
                            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = BandGreen) }
                        )

                        Spacer(Modifier.height(12.dp))
                        Divider(color = BordernSoft)
                        Spacer(Modifier.height(12.dp))

                        Text(
                            text = "Ubicacion (ComboBox)",
                            fontWeight = FontWeight.Bold,
                            color = TextMuted
                        )
                        Spacer(Modifier.height(10.dp))

                        ExposedDropdownMenuBox(
                            expanded = regionMenuExpanded,
                            onExpandedChange = { regionMenuExpanded = !regionMenuExpanded }
                        ) {
                            OutlinedTextField(
                                value = region,
                                onValueChange = {},
                                readOnly = true,
                                label = { Text("Region") },
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = regionMenuExpanded) },
                                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                                modifier = Modifier
                                    .menuAnchor()
                                    .fillMaxWidth()
                            )

                            ExposedDropdownMenu(
                                expanded = regionMenuExpanded,
                                onDismissRequest = { regionMenuExpanded = false }
                            ) {
                                regiones.forEach { r ->
                                    DropdownMenuItem(
                                        text = { Text(r) },
                                        onClick = {
                                            region = r
                                            regionMenuExpanded = false
                                        }
                                    )
                                }
                            }
                        }

                        Spacer(Modifier.height(10.dp))

                        AppOutlinedTextField(
                            value = comuna,
                            onValueChange = { comuna = it },
                            label = "Comuna",
                            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = BandGreen) }
                        )

                        Spacer(Modifier.height(10.dp))

                        AppOutlinedTextField(
                            value = telefono,
                            onValueChange = { telefono = it },
                            label = "Telefono",
                            leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null, tint = BandGreen) }
                        )

                        Spacer(Modifier.height(12.dp))
                        Divider(color = BordernSoft)
                        Spacer(Modifier.height(12.dp))

                        Text(
                            text = "Preferencias de accesibilidad",
                            fontWeight = FontWeight.Bold,
                            color = TextMuted
                        )
                        Spacer(Modifier.height(8.dp))

                        Text(text = "Modo de lectura (Radio Buttons)", color = TextMuted)
                        modosLectura.forEach { opt ->
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                RadioButton(
                                    selected = modoLectura == opt,
                                    onClick = { modoLectura = opt }
                                )
                                Text(text = opt)
                            }
                        }

                        Spacer(Modifier.height(8.dp))

                        Text(text = "Ayudas visuales (Check list)", color = TextMuted)
                        ayudas.forEach { a ->
                            val checked = ayudasSeleccionadas.contains(a)
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Checkbox(
                                    checked = checked,
                                    onCheckedChange = {
                                        if (it) ayudasSeleccionadas.add(a) else ayudasSeleccionadas.remove(a)
                                    },
                                    colors = CheckboxDefaults.colors(checkedColor = BandGreen)
                                )
                                Text(text = a)
                            }
                        }

                        Spacer(Modifier.height(12.dp))
                        Divider(color = BordernSoft)
                        Spacer(Modifier.height(12.dp))

                        Text(
                            text = "Credenciales",
                            fontWeight = FontWeight.Bold,
                            color = TextMuted
                        )
                        Spacer(Modifier.height(10.dp))

                        AppOutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
                            label = "Correo",
                            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = BandGreen) }
                        )
                        Spacer(Modifier.height(10.dp))

                        AppPasswordField(
                            value = password,
                            onValueChange = { password = it },
                            label = "Contrasena",
                            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = BandGreen) }
                        )
                        Spacer(Modifier.height(10.dp))

                        AppPasswordField(
                            value = confirmPassword,
                            onValueChange = { confirmPassword = it },
                            label = "Confirmar contrasena",
                            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = BandGreen) }
                        )

                        Spacer(Modifier.height(14.dp))

                        Button(
                            onClick = {
                                scope.launch {
                                    // ✅ límite de 5 usuarios: se mantiene como requisito "en memoria"
                                    if (!UserStore.canRegisterMore()) {
                                        snackbarHostState.showSnackbar("No se pueden registrar mas de 5 usuarios (memoria)")
                                        return@launch
                                    }

                                    if (!Validators.isValidRut(rut)) {
                                        snackbarHostState.showSnackbar("RUT invalido. Ej: 12345678-9")
                                        return@launch
                                    }
                                    if (!Validators.isValidPhone(telefono)) {
                                        snackbarHostState.showSnackbar("Telefono invalido (solo digitos, 8 a 12)")
                                        return@launch
                                    }
                                    if (!Validators.isValidEmail(email)) {
                                        snackbarHostState.showSnackbar("Correo invalido. Ej: usuario@dominio.cl")
                                        return@launch
                                    }
                                    if (!canSubmit) {
                                        snackbarHostState.showSnackbar("Completa campos, confirma contrasena y selecciona al menos 1 ayuda")
                                        return@launch
                                    }

                                    val user = User(
                                        email = email.trim().lowercase(),
                                        password = password,
                                        nombreCompleto = listOf(nombre, apellidoPaterno, apellidoMaterno)
                                            .map { it.trim() }
                                            .filter { it.isNotEmpty() }
                                            .joinToString(" "),
                                        rut = rut.trim().replace(".", "").uppercase(),
                                        region = region,
                                        comuna = comuna.trim(),
                                        telefono = telefono.onlyDigits(),
                                        modoLectura = modoLectura,
                                        ayudasVisuales = ayudasSeleccionadas.toList()
                                    )

                                    // ✅ Guardado local (requisito Sumativa 2) - opcional pero útil para tabla/grilla
                                    val local = UserStore.addUser(user)
                                    if (local.isFailure) {
                                        snackbarHostState.showSnackbar(local.exceptionOrNull()?.message ?: "No se pudo guardar localmente")
                                        return@launch
                                    }

                                    // ✅ Registro REAL en Firebase Auth + Firestore (opción B)
                                    authVm.register(
                                        email = user.email,
                                        password = user.password,
                                        name = user.nombreCompleto
                                    ) {
                                        scope.launch {
                                            snackbarHostState.showSnackbar("Usuario registrado en Firebase ✅")

                                            // limpiar formulario
                                            rut = ""
                                            nombre = ""
                                            apellidoPaterno = ""
                                            apellidoMaterno = ""
                                            comuna = ""
                                            telefono = ""
                                            email = ""
                                            password = ""
                                            confirmPassword = ""
                                            ayudasSeleccionadas.clear()

                                            // opcional: volver a login
                                            onLoginClick()
                                        }
                                    }
                                }
                            },
                            enabled = canSubmit,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(54.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = BandGreen,
                                disabledContainerColor = BandGreen.copy(alpha = 0.35f)
                            )
                        ) {
                            Text(text = "Registrarse", fontWeight = FontWeight.Bold, color = Color.White)
                        }

                        Spacer(Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "Ya tienes cuenta? ", color = TextMuted)
                            LinkText(text = "Inicia sesion", onClick = onLoginClick)
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))

                Text(text = "Usuarios registrados (Tabla)", fontWeight = FontWeight.Bold, color = TextDark)
                if (UserStore.users.isEmpty()) {
                    Text(text = "Aun no hay usuarios", color = TextMuted)
                } else {
                    UserTable(users = UserStore.users)
                }

                Spacer(Modifier.height(16.dp))

                Text(text = "Usuarios (Grilla)", fontWeight = FontWeight.Bold, color = TextDark)
                if (UserStore.users.isNotEmpty()) {
                    UserGrid(
                        users = UserStore.users,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(260.dp)
                    )
                }

                Spacer(Modifier.height(24.dp))
            }
        }
    }
}