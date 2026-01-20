package com.example.semana1pv.data

data class User(
    val email: String,
    val password: String,
    val nombreCompleto: String,
    val rut: String,
    val region: String,
    val comuna: String,
    val telefono: String,
    val modoLectura: String,
    val ayudasVisuales: List<String>
)
