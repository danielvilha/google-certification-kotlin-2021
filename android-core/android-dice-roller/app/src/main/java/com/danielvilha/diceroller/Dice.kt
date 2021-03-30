package com.danielvilha.diceroller

/**
 * Created by danielvilha on 29/03/21
 * https://github.com/danielvilha
 */
class Dice(val numSides: Int) {

    fun roll(): Int {
        return (1..numSides).random()
    }
}