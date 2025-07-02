package com.example.quizzy.data

import com.example.quizzy.model.Categorie
import com.example.quizzy.model.Quiz
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


// Die Basis-URL für alle API-Endpunkte der TheMealDB (muss mit "/" enden)
const val BASE_URL = "https://opentdb.com/"

private val logger = HttpLoggingInterceptor().apply {
    // Setzt das Logging-Level auf BODY, um vollständige Request- und Response-Daten zu sehen
    level = HttpLoggingInterceptor.Level.BODY
}
private val client = OkHttpClient.Builder()
    .addInterceptor(logger)
    .build()

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .client(client)
    .build()

interface OpenTriviaAPIService {

    @GET("api.php")
    suspend fun getRandomQuizes(@Query("amount") amountOfQuiz: Int = 5): Quiz

    @GET("api_category.php")
    suspend fun getCategories(): Categorie

    @GET("api.php")
    suspend fun getQuizQuestions(@Query("amount")amountQuiz: Int = 5, @Query("category") categorieInt: Int): Quiz


//    @GET("search.php")
//    suspend fun searchMeal(@Query("s") mealName: String): MealResponse
}

object OpenTriviaAPI {
    val retrofitService: OpenTriviaAPIService by lazy { retrofit.create(OpenTriviaAPIService::class.java) }
}