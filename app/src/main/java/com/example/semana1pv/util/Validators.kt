package com.example.semana1pv.util

/**
 * Validaciones simples (sin backend) para apoyar la UX.
 * Se mantienen "livianas" para no complicar la evaluación.
 */
object Validators {

    private val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")

    /** Email básico: usuario@dominio.tld */
    fun isValidEmail(email: String): Boolean = email.trim().matches(emailRegex)

    /** Teléfono: solo dígitos, largo 8-12 (permite fijo o móvil). */
    fun isValidPhone(phone: String): Boolean {
        val digits = phone.onlyDigits()
        return digits.length in 8..12
    }

    /** RUT: validación básica de formato (no calcula dígito verificador). */
    fun isValidRut(rut: String): Boolean {
        val clean = rut.trim().uppercase()
        // acepta 12345678-9 o 12345678-K (con o sin puntos)
        val normalized = clean.replace(".", "")
        return Regex("^\\d{7,8}-[0-9K]\$").matches(normalized)
    }
}

/** Extension: devuelve solo dígitos de un String. */
fun String.onlyDigits(): String = filter { it.isDigit() }
