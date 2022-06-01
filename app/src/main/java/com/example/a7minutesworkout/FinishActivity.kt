package com.example.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.a7minutesworkout.databinding.ActivityFinishBinding
import com.example.a7minutesworkout.roomdatabase.HistoryDao
import com.example.a7minutesworkout.roomdatabase.HistoryEntity
import com.example.a7minutesworkout.roomdatabase.WorkoutApp
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class FinishActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFinishBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarExercise)

        if (supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding.toolbarExercise.setNavigationOnClickListener {
            onBackPressed()
        }
        binding.btnFinish.setOnClickListener {
            finish()
        }
        val dao=(application as WorkoutApp).db.historyDao()
        addDataToDatabase(dao)


    }


    private fun addDataToDatabase(historyDao: HistoryDao){

        val c=Calendar.getInstance()
        val dataTime = c.time
        Log.e("Date",""+dataTime)

        val sdf = SimpleDateFormat("dd MMMM yyyy HH;mm:ss",Locale.getDefault())
        val date= sdf.format(dataTime)
        Log.e("Formetted Date:",""+date)

        lifecycleScope.launch {
            historyDao.insert(HistoryEntity(date))
            Log.e("Data","Add.....")
        }

    }

}