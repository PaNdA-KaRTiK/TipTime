package com.example.tiptime

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.example.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*
        *Using findViewById() can become cumbersome.
        *View binding makes it much easier and faster to call methods on the views in your UI.
         */

        binding.btnCalculate.setOnClickListener { calculateTip() }
        binding.etCostOfService.setOnKeyListener { view, keyCode, _ ->
            handleKeyEvent(view, keyCode)
        }
    }

    /*
    *Function to calculate the Total tip Generated
    *after the user input is performed
    */
    @SuppressLint("SetTextI18n")
    private fun calculateTip() {
        val stringInTextField =
            binding.tietCostOfService.text.toString()        //Extracting the String input
        val cost =
            stringInTextField.toDoubleOrNull()                          //Converting the string input to Double
        val tipPercentage =
            when (binding.rgTip.checkedRadioButtonId) {          //Extracting the Radio Button input
                R.id.rbTwentyPercent -> 0.20
                R.id.rbFifteenPercent -> 0.15
                else -> 0.10
            }
        if (cost == null) {                                                 //In case the user gave no string input
            binding.tvResult.text =
                "Tip Amount"                        //and hit the calculate button
            return
        }
        var tip =
            cost * tipPercentage                                  //Calculation of Total Tip Generated!
        val roundUp =
            binding.swcRoundUp.isChecked                      //Extracting Switch Input
        if (roundUp) {                                                  //Whether to RoundUp the tip or not
            tip = ceil(tip)
        }

        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        binding.tvResult.text = getString(R.string.Tip_Amount, formattedTip)
    }

    /*
    Function to Hide
    keyboard on Enter key
    */
    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}