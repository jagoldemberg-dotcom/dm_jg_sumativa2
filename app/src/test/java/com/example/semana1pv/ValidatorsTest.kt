package com.example.semana1pv

import com.example.semana1pv.util.Validators
import org.junit.Assert.*
import org.junit.Test

class ValidatorsTest {

    @Test
    fun valid_email() {
        assertTrue(Validators.isValidEmail("test@example.com"))
        assertFalse(Validators.isValidEmail("bad-email"))
    }

    @Test
    fun valid_rut_format() {
        assertTrue(Validators.isValidRut("12.345.678-9"))
        assertTrue(Validators.isValidRut("12345678-K"))
        assertFalse(Validators.isValidRut("123-4"))
    }
}
