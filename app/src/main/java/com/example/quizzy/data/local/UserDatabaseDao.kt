package com.example.quizzy.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.quizzy.model.User
import java.nio.file.attribute.AclEntryPermission

@Dao
interface UserDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Update
    suspend fun update(user: User)

    @Query("SELECT * FROM User")
    fun getAll(): LiveData<List<User>>

    @Query("DELETE FROM User WHERE id = :userId")
    suspend fun deleteById(userId: Int)

}