package com.example.a7minutesworkout.roomdatabase

import android.app.Application

class WorkoutApp:Application() {

    val db by lazy {
        HistoryDatabase.getInstant(this)
    }

}