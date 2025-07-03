package com.example.quizzy.data.local

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.quizzy.model.User

const val TAG = "RoomRepository"

class RoomRepository(private val database: UserDatabase) {

    val userList: LiveData<List<User>> = database.dao.getAll()

    suspend fun insert(user: User) {
        try {
            database.dao.insert(user)
        } catch (e: Exception) {
            Log.d(TAG,"fehler beim insert funktion im repo: ${e.message}")
        }
    }
}