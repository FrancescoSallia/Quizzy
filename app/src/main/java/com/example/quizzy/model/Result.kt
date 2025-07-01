package com.example.quizzy.model

import com.squareup.moshi.Json

data class Result(
    val category: String,
    @param:Json(name = "correct_answer")
    val correctAnswer: String,
    val difficulty: String,
    @param:Json(name = "incorrect_answers")
    val incorrectAnswers: List<String>,
    val question: String,
    val type: String
)