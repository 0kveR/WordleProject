package com.example.wordle_project

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private val wordList = FourLetterWordList
    private var guesses = 3
    private var wins = 0
    private lateinit var button: Button
    private lateinit var answer: TextView
    private lateinit var wordToGuess: String
    private lateinit var textField: EditText
    private lateinit var result: String
    private lateinit var winStreak: TextView
    private lateinit var viewGroupOne: Array<View>
    private lateinit var viewGroupTwo: Array<View>
    private lateinit var viewGroupThree: Array<View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Linking xml elements to variables
        button = findViewById(R.id.button)
        textField = findViewById(R.id.enter_answer)
        answer = findViewById(R.id.answer)
        winStreak = findViewById(R.id.winStreak)

        // Assigning views to group
        viewGroupOne = arrayOf(
            findViewById(R.id.guess_one_caption),
            findViewById(R.id.guess_one_check),
            findViewById(R.id.guess_one_answer),
            findViewById(R.id.guess_one_result)
        )

        viewGroupTwo = arrayOf(
            findViewById(R.id.guess_two_caption),
            findViewById(R.id.guess_two_check),
            findViewById(R.id.guess_two_answer),
            findViewById(R.id.guess_two_result)
        )

        viewGroupThree = arrayOf(
            findViewById(R.id.guess_three_caption),
            findViewById(R.id.guess_three_check),
            findViewById(R.id.guess_three_answer),
            findViewById(R.id.guess_three_result)
        )

        // Setting Word
        randomWord()

        button.setOnClickListener {
            if (guesses > 0) {
                guessButton()
            } else {
                resetButton()
            }
        }
    }

    private fun randomWord() {
        // Getting a random word
        wordToGuess = wordList.getRandomFourLetterWord().lowercase()
        Log.i("correctWord", wordToGuess)
        answer.text = wordToGuess
    }

    private fun guessButton() {
        if (textField.text.isBlank() || textField.text.length < 4) {
            Toast.makeText(this, "Enter a 4 letter word", Toast.LENGTH_SHORT).show()
        } else {
            result = checkGuess(textField.text.toString().lowercase())
        }

        when (guesses) {
            3 -> {
                viewGroupOne[1].visibility = View.VISIBLE
                viewGroupTwo[0].visibility = View.VISIBLE
                val answerField: TextView = viewGroupOne[2] as TextView
                val resultField: TextView = viewGroupOne[3] as TextView
                answerField.text = textField.text
                resultField.text = result
                answerField.visibility = View.VISIBLE
                resultField.visibility = View.VISIBLE
                textField.text.clear()

                if (result == "OOOO") {
                    setForWin()
                    return
                }
            }
            2 -> {
                viewGroupTwo[1].visibility = View.VISIBLE
                viewGroupThree[0].visibility = View.VISIBLE
                val answerField: TextView = viewGroupTwo[2] as TextView
                val resultField: TextView = viewGroupTwo[3] as TextView
                answerField.text = textField.text
                resultField.text = result
                answerField.visibility = View.VISIBLE
                resultField.visibility = View.VISIBLE
                textField.text.clear()

                if (result == "OOOO") {
                    setForWin()
                    return
                }
            }
            1 -> {
                viewGroupThree[1].visibility = View.VISIBLE
                val answerField: TextView = viewGroupThree[2] as TextView
                val resultField: TextView = viewGroupThree[3] as TextView
                answerField.text = textField.text
                resultField.text = result
                answerField.visibility = View.VISIBLE
                resultField.visibility = View.VISIBLE
                textField.text.clear()

                if (result == "OOOO") {
                    setForWin()
                    return
                }
            }
        }

        guesses--
        if (guesses == 0) {
            setForLoss()
        }
    }

    @SuppressLint("ResourceAsColor", "SetTextI18n")
    private fun setForWin() {
        guesses = 0
        answer.visibility = View.VISIBLE
        wins++
        winStreak.text = "Current Wins: $wins"
        button.setBackgroundColor(R.color.button_win)
        Toast.makeText(this, "Congrats! You Won!", Toast.LENGTH_SHORT).show()
        button.text = "Retry?"
    }

    @SuppressLint("ResourceAsColor", "SetTextI18n")
    private fun setForLoss() {
        answer.visibility = View.VISIBLE
        button.setBackgroundColor(R.color.button_lose)
        if (wins > 0) {
            wins = 0
            winStreak.text = "Current Wins: $wins"
        }

        button.text = "Retry?"
    }

    @SuppressLint("ResourceAsColor", "SetTextI18n")
    private fun resetButton() {
        // Reset Button
        button.setBackgroundColor(R.color.button_default)
        button.text = "Guess!"
        guesses = 3
        textField.text.clear()

        // Reset Visibilities
        for (i in 1..3) {
            makeInvisible(viewGroupOne[i]) // Group 1 uses a different loop because the caption must stay visible
        }

        for(v in viewGroupTwo) {
            makeInvisible(v)
        }

        for (v in viewGroupThree) {
            makeInvisible(v)
        }
        makeInvisible(answer)

        randomWord()
    }

    private fun makeInvisible(v: View) {
        v.visibility = View.INVISIBLE
    }

    /**
     * Parameters / Fields:
     *   wordToGuess : String - the target word the user is trying to guess
     *   guess : String - what the user entered as their guess
     *
     * Returns a String of 'O', '+', and 'X', where:
     *   'O' represents the right letter in the right place
     *   '+' represents the right letter in the wrong place
     *   'X' represents a letter not in the target word
     */
    private fun checkGuess(guess: String) : String {
        var result = ""
        for (i in 0..3) {
            if (guess[i] == wordToGuess[i]) {
                result += "O"
            }
            else if (guess[i] in wordToGuess) {
                result += "+"
            }
            else {
                result += "X"
            }
        }
        return result
    }
}