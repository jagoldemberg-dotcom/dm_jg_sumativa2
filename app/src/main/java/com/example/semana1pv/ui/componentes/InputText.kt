package com.example.semana1pv.ui.componentes

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.semana1pv.ui.theme.BandGreen
import com.example.semana1pv.ui.theme.BackgroundLigth

@Composable
fun AppOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    leadingIcon: (@Composable (() -> Unit))? = null,
    singleLine: Boolean = true,
    enabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        label = { Text(label) },
        leadingIcon = leadingIcon,
        singleLine = singleLine,
        enabled = enabled,
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = BandGreen,
            unfocusedBorderColor = BackgroundLigth,
            focusedLabelColor = BandGreen,
            cursorColor = BandGreen,
            disabledBorderColor = BackgroundLigth,
            disabledLabelColor = Color.Gray
        )
    )
}

@Composable
fun AppPasswordField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    leadingIcon: (@Composable (() -> Unit))? = null,
    modifier: Modifier = Modifier
) {
    val show = remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        label = { Text(label) },
        leadingIcon = leadingIcon,
        singleLine = true,
        visualTransformation = if (show.value) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = { show.value = !show.value }) {
                Icon(
                    imageVector = if (show.value) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                    contentDescription = if (show.value) "Ocultar contrasena" else "Mostrar contrasena"
                )
            }
        },
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = BandGreen,
            unfocusedBorderColor = BackgroundLigth,
            focusedLabelColor = BandGreen,
            cursorColor = BandGreen
        )
    )
}
