package com.example.semana1pv.device

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority

class DeviceSearchFragment : Fragment() {

    private var onPermissionResult: ((Boolean) -> Unit)? = null

    private val requestLocationPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            onPermissionResult?.invoke(granted)
            onPermissionResult = null
        }

    override fun onCreateView(
        inflater: android.view.LayoutInflater,
        container: android.view.ViewGroup?,
        savedInstanceState: Bundle?
    ): android.view.View {
        return ComposeView(requireContext()).apply {
            setContent {
                DeviceSearchScreen(
                    onBack = {
                        requireActivity().onBackPressedDispatcher.onBackPressed()
                    },
                    requestPermission = { callback ->
                        val alreadyGranted = ContextCompat.checkSelfPermission(
                            requireContext(),
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED

                        if (alreadyGranted) {
                            callback(true)
                        } else {
                            onPermissionResult = callback
                            requestLocationPermission.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun DeviceSearchScreen(
    onBack: () -> Unit,
    requestPermission: ((Boolean) -> Unit) -> Unit
) {
    var status by remember { mutableStateOf("Presiona el botón para obtener tu ubicación.") }
    var lastLatLng by remember { mutableStateOf<Pair<Double, Double>?>(null) }

    val context = androidx.compose.ui.platform.LocalContext.current
    val fused = remember { LocationServices.getFusedLocationProviderClient(context) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(text = "Buscar dispositivo (Geolocalización)")
        Text(text = status)

        lastLatLng?.let { (lat, lng) ->
            Text(text = "Lat: $lat")
            Text(text = "Lng: $lng")
        }

        Button(
            onClick = {
                requestPermission { granted ->
                    if (!granted) {
                        status = "Permiso de ubicación denegado."
                        return@requestPermission
                    }

                    status = "Obteniendo ubicación..."

                    fused.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
                        .addOnSuccessListener { loc ->
                            if (loc == null) {
                                status = "No se pudo obtener ubicación (activa GPS o ubicación del emulador)."
                            } else {
                                lastLatLng = loc.latitude to loc.longitude
                                status = "Ubicación OK ✅"
                            }
                        }
                        .addOnFailureListener { e ->
                            status = "Error: ${e.message ?: "no disponible"}"
                        }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Obtener mi ubicación")
        }

        // ✅ Botón volver
        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver atrás")
        }
    }
}