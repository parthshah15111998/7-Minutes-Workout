package com.example.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a7minutesworkout.adapter.HistoryAdapter
import com.example.a7minutesworkout.databinding.ActivityHistoryBinding
import com.example.a7minutesworkout.roomdatabase.HistoryDao
import com.example.a7minutesworkout.roomdatabase.WorkoutApp
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarHistoryActivity)
        if (supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title="History "
        }
        binding.toolbarHistoryActivity.setNavigationOnClickListener {
            onBackPressed()
        }

        val dao=(application as WorkoutApp).db.historyDao()
        getAllCompletedDates(dao)


    }

    private fun getAllCompletedDates(historyDao: HistoryDao){
        lifecycleScope.launch {
            historyDao.fetchAllDates().collect {allCompletedDataList->
                if (allCompletedDataList.isNotEmpty()){
                    binding.tvHistory.visibility=View.VISIBLE
                    binding.rvHistory.visibility=View.VISIBLE
                    binding.tvNoDataAvailable.visibility=View.INVISIBLE
                    binding.rvHistory.layoutManager=LinearLayoutManager(this@HistoryActivity)

                    val dates=ArrayList<String>()
                    for(date in allCompletedDataList){
                        dates.add(date.date)
                    }

                    val historyAdapter=HistoryAdapter(dates)
                    binding.rvHistory.adapter=historyAdapter

                }
                else{
                    binding.tvHistory.visibility=View.GONE
                    binding.rvHistory.visibility=View.GONE
                    binding.tvNoDataAvailable.visibility=View.VISIBLE
                }
            }
        }
    }


}