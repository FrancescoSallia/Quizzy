package com.example.quizzy.model

import com.squareup.moshi.Json

data class Quiz(

    @param:Json(name = "response_code")
    val responseCode: Int,
    val results: List<Result>

)