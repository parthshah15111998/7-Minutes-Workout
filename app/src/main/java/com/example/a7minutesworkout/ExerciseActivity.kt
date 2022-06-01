package com.example.a7minutesworkout

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a7minutesworkout.adapter.ExerciseStatusAdapter
import com.example.a7minutesworkout.databinding.ActivityExerciseBinding
import com.example.a7minutesworkout.databinding.DialogCustomBackConfirmationBinding
import com.example.a7minutesworkout.model.ExerciseModel
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var binding: ActivityExerciseBinding
    private var restTimer:CountDownTimer? = null
    private var restProgress = 0
    private var exerciseTimer:CountDownTimer? = null
    private var exerciseProgress = 0
    private var exerciseList:ArrayList<ExerciseModel>? = null
    private var currentExercisePosition= -1
    private var restTimeDuration:Long=1
    private var exerciseTimeDuration:Long=1
    private var tts: TextToSpeech? = null
    private var player:MediaPlayer?=null
    private var exerciseStatusAdapter:ExerciseStatusAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarExercise)

        if (supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        tts= TextToSpeech(this,this)
        exerciseList = Constants.defaultExerciseList()

        binding.toolbarExercise.setNavigationOnClickListener {
            customDialogForBackButton()
        }
        setupRestView()
        setUpExerciseStatusRecyclerView()
    }

    override fun onBackPressed() {
        customDialogForBackButton()
        //super.onBackPressed()

    }

    private fun customDialogForBackButton() {

        val customDialog=Dialog(this)
        val dialogBinding=DialogCustomBackConfirmationBinding.inflate(layoutInflater)
        customDialog.setContentView(dialogBinding.root)
        customDialog.setCanceledOnTouchOutside(false)
        dialogBinding.btnYes.setOnClickListener {
            this@ExerciseActivity.finish()
            customDialog.dismiss()
        }
        dialogBinding.btnNo.setOnClickListener {
            customDialog.dismiss()
        }
        customDialog.show()

    }

    private fun setUpExerciseStatusRecyclerView(){
        binding.rvExerciseStatus.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
            exerciseStatusAdapter= ExerciseStatusAdapter(exerciseList!!)
        binding.rvExerciseStatus.adapter=exerciseStatusAdapter
    }

    private fun setupRestView(){
        try {
            val soundURL = Uri.parse("" +R.raw.press_start)
            player = MediaPlayer.create(applicationContext,soundURL)
            player?.isLooping = false
            player?.start()

        }catch (e:Exception){
            e.printStackTrace()
        }

        binding.flRestView.visibility = View.VISIBLE
        binding.tvGetReady.visibility=View.VISIBLE
        binding.tvExercise.visibility=View.INVISIBLE
        binding.flExerciseView.visibility = View.INVISIBLE
        binding.imgExerciseImage.visibility=View.INVISIBLE
        binding.tvUpComingLabel.visibility=View.VISIBLE
        binding.tvUpComingExerciseName.visibility=View.VISIBLE

        if(restTimer != null){
            restTimer?.cancel()
            restProgress = 0
        }


        binding.tvUpComingExerciseName.text=exerciseList!![currentExercisePosition + 1].getName()


        setRestProgressBar()
    }
    private fun setupExerciseView(){
       binding.flRestView.visibility = View.INVISIBLE
       binding.tvGetReady.visibility=View.INVISIBLE
       binding.tvExercise.visibility=View.VISIBLE
       binding.flExerciseView.visibility = View.VISIBLE
       binding.imgExerciseImage.visibility=View.VISIBLE
        binding.tvUpComingLabel.visibility=View.INVISIBLE
        binding.tvUpComingExerciseName.visibility=View.INVISIBLE


        if (exerciseTimer != null){
            exerciseTimer?.cancel()
            exerciseProgress=0
        }

        speakOut(exerciseList!![currentExercisePosition].getName())
        binding.imgExerciseImage.setImageResource(exerciseList!![currentExercisePosition].getImage())
        binding.tvExercise.text=exerciseList!![currentExercisePosition].getName()

        setExerciseProgressBar()
    }


    private fun setRestProgressBar(){
        binding.progressBar.progress = restProgress
        restTimer = object:CountDownTimer(restTimeDuration*10000,1000){
            override fun onTick(p0: Long) {
                restProgress++
                binding.progressBar.progress = 10 - restProgress
                binding.tvTimer.text=(10-restProgress).toString()
            }

            override fun onFinish() {
                currentExercisePosition++

                exerciseList!![currentExercisePosition].setIsSelected(true)
                exerciseStatusAdapter!!.notifyDataSetChanged()

                setupExerciseView()
            }
        }.start()
    }

    private fun setExerciseProgressBar(){
        binding.progressBarExercise.progress = exerciseProgress
        exerciseTimer = object:CountDownTimer(exerciseTimeDuration *30000,1000){
            override fun onTick(p0: Long) {
                exerciseProgress++
                binding.progressBarExercise.progress = 30 - exerciseProgress
                binding.tvTimerExercise.text=(30-exerciseProgress).toString()
            }

            override fun onFinish() {

                if (currentExercisePosition < exerciseList?.size!! - 1){
                    exerciseList!![currentExercisePosition].setIsSelected(false)
                    exerciseList!![currentExercisePosition].setIsCompleted(true)
                    exerciseStatusAdapter!!.notifyDataSetChanged()

                    setupRestView()
                }else{
                finish()
                    val intent= Intent(this@ExerciseActivity,FinishActivity::class.java)
                    startActivity(intent)
                //Toast.makeText(this@ExerciseActivity,"Congratulation",Toast.LENGTH_SHORT).show()
                }
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()

        if (restTimer != null){
            restTimer?.cancel()
            restProgress=0
        }
        if (exerciseTimer != null){
            exerciseTimer?.cancel()
            exerciseProgress=0
        }
        if (tts != null){
            tts?.stop()
            tts?.shutdown()
        }
        if (player != null){
            player!!.stop()

        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS){
            val result= tts?.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA ||
                result == TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("TTS","Language Is Not Support")
            }
        }else{
            Log.e("TTS","Failed")
        }
    }

    private fun speakOut(text:String){
        tts!!.speak(text,TextToSpeech.QUEUE_FLUSH,null,"")
    }

}