package com.example.quizzy.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey()
    var id: Int = 0,
    var rightAnswerList: List<Int>,
    var wrongAnswerList: List<Int>
)