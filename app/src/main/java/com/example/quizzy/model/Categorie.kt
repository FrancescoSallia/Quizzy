package com.example.quizzy.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Categorie(

    @param:Json(name = "trivia_categories")
    val triviaCategories: List<TriviaCategory>
):  Parcelable