package com.example.quizzy.model

import android.os.Parcelable
import com.example.quizzy.R
import com.example.quizzy.data.repository.getCategoryDrawable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializer

@Parcelize
data class Result(
    val category: String,
    @param:Json(name = "correct_answer")
    val correctAnswer: String,
    val difficulty: String,
    @param:Json(name = "incorrect_answers")
    val incorrectAnswers: List<String>,
    val question: String,
    val type: String
    ): Parcelable