package com.example.semana1pv.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.semana1pv.R
import com.example.semana1pv.ui.componentes.AppOutlinedTextField
import com.example.semana1pv.ui.componentes.AppPasswordField
import com.example.semana1pv.ui.componentes.LinkText
import com.example.semana1pv.ui.theme.BackgroundLigth
import com.example.semana1pv.ui.theme.BandGreen
import com.example.semana1pv.ui.theme.BordernSoft
import com.example.semana1pv.ui.theme.TextDark
import com.example.semana1pv.ui.theme.TextMuted
import com.example.semana1pv.ui.viewmodel.AuthState
import com.example.semana1pv.ui.viewmodel.AuthViewModel
import com.example.semana1pv.util.Validators
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit = {},
    onRegistroClick: () -> Unit = {},
    onForgotClick: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }
    val authVm: AuthViewModel = viewModel()
    val authState by authVm.state.collectAsState()
    val scope = rememberCoroutineScope()

    // Mensajes de error desde el ViewModel
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
                    .imePadding()
                    .navigationBarsPadding()
                    .padding(18.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(5.dp))

                Card(
                    shape = CircleShape,
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .padding(18.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription = "Logo",
                            modifier = Modifier.size(140.dp),
                            tint = Color.Unspecified
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "Muy bienvenido",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.ExtraBold,
                    color = TextDark
                )

                Spacer(modifier = Modifier.height(3.dp))

                Text(
                    text = "Porfavor inicia sesion",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextMuted
                )

                Spacer(modifier = Modifier.height(10.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text(
                            text = "Entra por redes sociales",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            color = TextMuted,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            SocialCircle(label = "G")
                            Spacer(modifier = Modifier.width(12.dp))
                            SocialCircle(label = "X")
                            Spacer(modifier = Modifier.width(12.dp))
                            SocialCircle(label = "I")
                        }

                        Spacer(modifier = Modifier.height(6.dp))
                        Divider(color = BordernSoft, thickness = 1.dp)
                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "o inicia con Email",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            color = TextMuted,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        AppOutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
                            label = "Correo electronico",
                            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = Blue) },
                            modifier = Modifier.padding(horizontal = 6.dp)
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        AppPasswordField(
                            value = password,
                            onValueChange = { password = it },
                            label = "Contrasena",
                            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = Blue) },
                            modifier = Modifier.padding(horizontal = 12.dp)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = rememberMe,
                                onCheckedChange = { rememberMe = it },
                                colors = CheckboxDefaults.colors(checkedColor = Blue)
                            )
                            Text(text = "Recuerdame", color = TextMuted)
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp),
                            horizontalArrangement = Arrangement.End
                        ) {
                            LinkText(text = "Recupera tu contrasenia?", onClick = onForgotClick)
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Button(
                            onClick = {
                                scope.launch {
                                    val cleanEmail = email.trim().lowercase()
                                    val cleanPass = password

                                    if (cleanEmail.isBlank() || cleanPass.isBlank()) {
                                        snackbarHostState.showSnackbar("Completa correo y contraseña")
                                        return@launch
                                    }
                                    if (!Validators.isValidEmail(cleanEmail)) {
                                        snackbarHostState.showSnackbar("Correo inválido")
                                        return@launch
                                    }

                                    // ✅ Login REAL en Firebase
                                    authVm.login(cleanEmail, cleanPass) {
                                        onLoginSuccess()
                                    }
                                }
                            },
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .fillMaxWidth(0.8f)
                                .height(35.dp),
                            shape = MaterialTheme.shapes.medium,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Blue,
                                contentColor = Color.White
                            )
                        ) {
                            Text(
                                text = "Iniciemos",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "Sin cuenta? ", color = TextMuted)
                            LinkText(text = "Registrate", onClick = onRegistroClick)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SocialCircle(label: String) {
    Box(
        modifier = Modifier
            .size(32.dp)
            .clip(CircleShape)
            .background(Color.White)
            .border(1.dp, Blue.copy(alpha = 0.35f), CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            color = BandGreen,
            fontWeight = FontWeight.ExtraBold,
            style = MaterialTheme.typography.titleMedium
        )
    }
}