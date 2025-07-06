package com.example.quizzy.data.local

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import com.example.quizzy.model.User

const val TAG = "RoomRepository"

class RoomRepository(private val database: UserDatabase) {

    val userList: LiveData<List<User>> = database.dao.getAll()

    suspend fun insert(user: User, context: Context) {
        try {
            database.dao.insert(user)
        } catch (e: Exception) {
            Log.d(TAG,"Fehler beim insert funktion im RoomRepo: ${e.message}")
            Toast.makeText(context, "${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    suspend fun deleteUser(userId: Int, context: Context){
       try {
            database.dao.deleteById(userId)
       } catch(e: Exception) {
           Log.d(TAG,"Fehler beim deleteUser funktion im RoomRepo: ${e.message}")
           Toast.makeText(context, "${e.message}", Toast.LENGTH_SHORT).show()
       }
    }
}