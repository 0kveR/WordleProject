package com.example.wordle_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar

class HelpPage : AppCompatActivity() {
    private lateinit var closeButton: ImageView
    private lateinit var generalButton: Button
    private lateinit var animalButton: Button
    private lateinit var foodButton: Button
    private lateinit var bg: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help_page)

        var category = intent.getSerializableExtra("Category") as? FourLetterWordList.ListType

        if (category == null) {
            category = FourLetterWordList.ListType.GENERAL
        }

        closeButton = findViewById(R.id.closeButton)
        generalButton = findViewById(R.id.generalButton)
        animalButton = findViewById(R.id.animalsButton)
        foodButton = findViewById(R.id.foodButton)
        bg = findViewById(R.id.background)

        closeButton.setOnClickListener {
            val returnData = Intent()
            returnData.putExtra("Category", category)
            setResult(RESULT_OK, returnData)
            finish()
        }
        generalButton.setOnClickListener {
            category = FourLetterWordList.ListType.GENERAL
            Snackbar.make(bg, "Category set to General", Snackbar.LENGTH_SHORT).show()
        }
        animalButton.setOnClickListener {
            category = FourLetterWordList.ListType.ANIMALS
            Snackbar.make(bg, "Category set to Animals", Snackbar.LENGTH_SHORT).show()
        }
        foodButton.setOnClickListener {
            category = FourLetterWordList.ListType.FOOD
            Snackbar.make(bg, "Category set to Food", Snackbar.LENGTH_SHORT).show()
        }
    }
}