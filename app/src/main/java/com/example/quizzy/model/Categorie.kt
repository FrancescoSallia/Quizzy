package com.example.quizzy.model

import com.squareup.moshi.Json

data class Categorie(

    @param:Json(name = "trivia_categories")
    val triviaCategories: List<TriviaCategory>
)