package com.example.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.a7minutesworkout.databinding.ActivityBmiactivityBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {

    private lateinit var binding:ActivityBmiactivityBinding

    companion object{
        private const val METRIC_UNITS_VIEW="METRIC_UNITS_VIEW"
        private const val US_UNITS_VIEW="US_UNITS_VIEW"
    }

    private var currentVisibleView:String = METRIC_UNITS_VIEW

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityBmiactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarBmiActivity)
        if (supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title="Calculator BMI"
        }
        binding.toolbarBmiActivity.setNavigationOnClickListener {
            onBackPressed()
        }

        makeVisibleMetricUnitsView()
        binding.rgUnits.setOnClickListener {

        }
        binding.rgUnits.setOnCheckedChangeListener{_,checkedId:Int ->
            if (checkedId == R.id.rb_metricUnits){
                makeVisibleMetricUnitsView()
            }else{
                makeVisibleUsUnitsView()
            }
        }

        binding.btnCalculateUnits.setOnClickListener {
            calculateUnit()
        }
    }

    private fun makeVisibleMetricUnitsView(){
        currentVisibleView= METRIC_UNITS_VIEW

        binding.tillMetricUnitWeight.visibility=View.VISIBLE
        binding.tillMetricUnitHeight.visibility=View.VISIBLE
        binding.tilUsMetricUnitWeight.visibility=View.GONE
        binding.tilMetricUsUnitHeightFeet.visibility=View.GONE
        binding.tilMetricUsUnitHeightInch.visibility=View.GONE

        binding.etMetricUnitWeight.text!!.clear()
        binding.etMetricUnitHeight.text!!.clear()
    }

    private fun makeVisibleUsUnitsView(){
        currentVisibleView= US_UNITS_VIEW

        binding.tillMetricUnitWeight.visibility=View.INVISIBLE
        binding.tillMetricUnitHeight.visibility=View.INVISIBLE
        binding.tilUsMetricUnitWeight.visibility=View.VISIBLE
        binding.tilMetricUsUnitHeightFeet.visibility=View.VISIBLE
        binding.tilMetricUsUnitHeightInch.visibility=View.VISIBLE

        binding.etUsMetricUnitHeightFeet.text!!.clear()
        binding.etUsMetricUnitHeightInch.text!!.clear()
        binding.etUsMetricUnitWeight.text!!.clear()

        binding?.llBmiDisplayResult.visibility = View.INVISIBLE
    }

    private fun displayBMIResult(bmi:Float){

        val bmiLabel:String
        val bmiDescription:String

        when {
            bmi.compareTo(15f) <= 0 -> {
                bmiLabel="Very severely underweight"
                bmiDescription="Oops! You really need to take better car of yourSelf"
            }
            bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0 -> {
                bmiLabel=" severely underweight"
                bmiDescription="Oops! You really need to take better car of yourSelf"
            }
            bmi.compareTo(16f)>0 && bmi.compareTo(18.05) <= 0 -> {
                bmiLabel="underweight"
                bmiDescription="Oops! You really need to take better car of yourSelf"
            }
            bmi.compareTo(18.5f)>0 && bmi.compareTo(25) <= 0 -> {
                bmiLabel="normal"
                bmiDescription="Congratulation you are good"
            }
            bmi.compareTo(25f)>0 && bmi.compareTo(30) <= 0 -> {
                bmiLabel="OverWeight"
                bmiDescription="Oops! You really need to take better car of yourSelf"
            }
            bmi.compareTo(30f)>0 && bmi.compareTo(35) <= 0 -> {
                bmiLabel="Obese Class | (Moderately obese)"
                bmiDescription="Oops! You really need to take better car of yourSelf"
            }
            bmi.compareTo(35f)>0 && bmi.compareTo(40) <= 0 -> {
                bmiLabel="Obese Class || (Severely obese)"
                bmiDescription="Oops! You really need to take better car of yourSelf"
            }
            else -> {
                bmiLabel="Obese Class ||| ( very severely obese)"
                bmiDescription="Oops! You really need to take better car of yourSelf"
            }
        }

        val bmiValue=BigDecimal(bmi.toDouble()).setScale(2,RoundingMode.HALF_EVEN).toString()

        binding.tvBmiValue.text=bmiValue
        binding.tvBmiType.text=bmiLabel
        binding.tvBmiDescription.text=bmiDescription
        binding.llBmiDisplayResult.visibility= View.VISIBLE
    }

    private fun calculateUnit(){
        if (currentVisibleView == METRIC_UNITS_VIEW){
            if (validateMetricUnits()){
                val heightValue:Float = binding.etMetricUnitHeight.text.toString().toFloat()/100
                val weightValue:Float = binding.etMetricUnitWeight.text.toString().toFloat()

                val bmi=weightValue/(heightValue * heightValue)
                displayBMIResult(bmi)
            }else{
                Toast.makeText(this,"Please Enter Valid Value",Toast.LENGTH_SHORT).show()
            }
        }else{
            if (validateUsUnits()){
                val usUnitHeightValueFeet:String=
                    binding.etUsMetricUnitHeightFeet.text.toString()
                val usUnitHeightValueInch:String=
                    binding.etUsMetricUnitHeightInch.text.toString()
                val usUnitWeightValue:Float=
                    binding.etUsMetricUnitWeight.text.toString().toFloat()

                val heightValue=
                    usUnitHeightValueInch.toFloat()+usUnitHeightValueFeet.toFloat()*12

                val bmi= 703 * (usUnitWeightValue/(heightValue * heightValue))

                displayBMIResult(bmi)


            }else{
                Toast.makeText(this,"Please Enter Valid Value",Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun validateMetricUnits():Boolean{
        var isValid = true

        when {
            binding.etMetricUnitWeight.text.toString().isEmpty() -> {
                isValid=false
            }
            binding.etMetricUnitHeight.text.toString().isEmpty() -> {
                isValid=false
            }
        }
        return isValid
    }

    private fun validateUsUnits():Boolean{
        var isValid = true

        when {
            binding.etUsMetricUnitWeight.text.toString().isEmpty()->{
                isValid=false
            }
            binding.etUsMetricUnitHeightFeet.text.toString().isEmpty() -> {
                isValid=false
            }
            binding.etUsMetricUnitHeightInch.text.toString().isEmpty() -> {
                isValid=false
            }
        }
        return isValid
    }
}