package com.example.insurancecalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    override fun onNothingSelected(parent: AdapterView<*>?){

    }

    override fun onItemSelected(parent: AdapterView<*>?,
                                view: View?,
                                position: Int,
                                id: Long) {
        val selectedItem = spinnerAge.getItemIdAtPosition(position)
        /*val selectedItem = spinnerAge.selectedItemPosition*/
        Toast.makeText(this,
            "Selected Item ="+ selectedItem,
        Toast.LENGTH_LONG)
        .show()
    }

    /*override fun onClick(v: View?){
        when(v){
            buttonCalculate->calculatePremium()
            buttonReset->reset()
        }
    }*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Handong item selected listener for spinner
        spinnerAge.onItemSelectedListener = this

        buttonCalculate.setOnClickListener {
            calculatePremium()
        }
        buttonReset.setOnClickListener {
            reset()
        }

        /*val buttonThrid: Button
        buttonThrid.setOnClickListener(this)*/
    }

    private fun calculatePremium(){
        //get the age group. Position of an array
        val age: Int = spinnerAge.selectedItemPosition
        var premium:Int = when(age){
            0 -> 60 //less than 17
            1 -> 70 //17-25
            2 -> 90 //26-30.
            3 -> 120 //31-40
            else -> 150
        }

        //Get gender selection. ID of radio button
        val gender = radioGroupGender.checkedRadioButtonId
        var premiumGender:Int = 0
        if(gender == R.id.radioButtonMale){
            //calculate premium for male
            premiumGender = when(age){
                0 -> 0
                1 -> 50 //17-25
                2 -> 100 //26-30.
                3 -> 150 //31-40
                else -> 200 //>41
            }

        }

        //determine smoker or non-smoker
        val smoker: Boolean = checkBoxSmoker.isChecked
        var premiumSmoker:Int =0
        if(smoker){
            //Calculate premium for female
            premiumSmoker = when(age){
                0 -> 0
                1 -> 100 //17-25
                2 -> 150 //26-30.
                3 -> 200 //31-40
                4 -> 250 //>41-55
                else -> 300 //>55
            }
        }

        val symbol = Currency.getInstance(Locale.getDefault()).symbol
        var premiumTotal = premium+premiumGender+premiumSmoker
        textViewPremium.text = String.format("%s %d",symbol,premiumTotal)
    }

    private fun reset() {
        spinnerAge.setSelection(0)
        radioGroupGender.clearCheck()
        textViewPremium.text=getString(R.string.insurance_premium)
        checkBoxSmoker.setChecked(false)
    }
}
