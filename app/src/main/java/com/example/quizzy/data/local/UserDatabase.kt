package com.example.quizzy.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.quizzy.model.User

@Database(entities = [User::class], version = 1)
@TypeConverters(Converters::class)
abstract  class UserDatabase: RoomDatabase() {

    abstract val dao: UserDatabaseDao

}
private lateinit var INSTANCE: UserDatabase

fun getDatabase(context: Context): UserDatabase {

    synchronized(UserDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {

            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                UserDatabase::class.java,
                "user_database"
            ).build()
        }
        return INSTANCE
    }
}