package com.example.quizzy.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var rightAnswer: Int,
    var wrongAnswer: Int
)