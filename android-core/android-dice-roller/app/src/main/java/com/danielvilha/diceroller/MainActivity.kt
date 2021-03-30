package com.danielvilha.diceroller

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

/**
 * This activity allows the user to roll a dice and view the result
 * on the screen.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val spinner: Spinner = findViewById(R.id.spinner)
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
                this,
                R.array.rolls,
                android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }

        val howMuchTextView: TextView = findViewById(R.id.editTextNumberDecimal)

        val rollButton: Button = findViewById(R.id.buttonRoll)
        rollButton.setOnClickListener {
            rollDices(howMuchTextView.text.toString(), spinner.selectedItem.toString().toInt())
        }
    }

    /**
     * Roll the dice and update the screen with the result.
     */
    private fun rollDices(howMuch: String, numSides: Int) {
        var _howMuch = if (howMuch != "") howMuch.toInt() else 1
        // Create new Dice object with the number sides and roll it
        val dice = Dice(numSides)
        // Update the screen with the dice roll
        val resultTextView: TextView = findViewById(R.id.textViewNumRolled)
        var _result = ""
        while (_howMuch > 0) {
            val diceRoll = dice.roll()
            _result = if (_howMuch <= 1) "$_result $diceRoll" else "$_result $diceRoll - "
            _howMuch--
        }

        resultTextView.text = _result
    }
}