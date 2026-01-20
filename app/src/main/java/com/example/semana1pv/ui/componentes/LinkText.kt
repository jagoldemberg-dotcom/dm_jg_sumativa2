package com.example.semana1pv.ui.componentes

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.semana1pv.ui.theme.BrandOrange

@Composable
fun LinkText(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        color = BrandOrange,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        modifier = modifier.clickable { onClick() }
    )
}
