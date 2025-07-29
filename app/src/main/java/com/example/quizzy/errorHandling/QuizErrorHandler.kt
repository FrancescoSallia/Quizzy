package com.example.quizzy.errorHandling

import android.content.Context

object QuizErrorHandler {

    fun mapExceptionToErrorType(e: Exception): QuizErrorType {
        return when (e) {
            is java.net.UnknownHostException -> QuizErrorType.NETWORK
            is java.net.SocketTimeoutException -> QuizErrorType.TIMEOUT
            is retrofit2.HttpException -> QuizErrorType.SERVER
            else -> QuizErrorType.UNKNOWN
        }
    }

    fun getErrorMessage(context: Context, errorType: QuizErrorType, customMessage: String?): String {
        return when (errorType) {
            QuizErrorType.NETWORK -> "Keine Internetverbindung."
            QuizErrorType.TIMEOUT -> "Die Anfrage hat zu lange gedauert."
            QuizErrorType.SERVER -> "Serverfehler. Bitte versuche es später erneut."
            QuizErrorType.UNKNOWN -> "Ein unbekannter Fehler ist aufgetreten."
            QuizErrorType.CUSTOM -> customMessage ?: "Ein Fehler ist aufgetreten."
        }
    }
}