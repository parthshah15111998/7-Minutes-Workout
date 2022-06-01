package com.example.a7minutesworkout.roomdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [HistoryEntity::class], version = 1)
abstract class HistoryDatabase: RoomDatabase() {

    abstract fun historyDao():HistoryDao

    companion object{
        private var INSTANT:HistoryDatabase ? = null

        fun getInstant(context: Context):HistoryDatabase{
            synchronized(this){
                var instant= INSTANT

                if (instant == null){
                    instant = Room.databaseBuilder(
                        context.applicationContext,
                        HistoryDatabase::class.java,
                        "history_database"
                    ).fallbackToDestructiveMigration().build()

                    INSTANT=instant
                }
                return instant
            }
        }
    }


}