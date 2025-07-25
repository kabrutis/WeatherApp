package com.example.weatherapp

import com.example.weatherapp.domain.models.ErrorType
import org.junit.Assert.assertEquals
import org.junit.Test

class ErrorTypeTest {

    @Test
    fun `map 404 message to NETWORK error`() {
        val e = Exception("HTTP 404 error")
        val type = ErrorType.fromExceptionMessage(e.message)
        assertEquals(ErrorType.Network, type)
    }

    @Test
    fun `map timeout message to TIMEOUT error`() {
        val e = Exception("oops timeout")
        val type = ErrorType.fromExceptionMessage(e.message)
        assertEquals(ErrorType.Timeout, type)
    }

    @Test
    fun `map unknown error when message does not match`() {
        val e = Exception("this is some different error")
        val type = ErrorType.fromExceptionMessage(e.message)
        assertEquals(ErrorType.Unknown, type)
    }
}