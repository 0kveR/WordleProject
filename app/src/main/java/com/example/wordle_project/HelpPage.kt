package com.example.wordle_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class HelpPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help_page)

        val closeButton = findViewById<ImageView>(R.id.closeButton)

        closeButton.setOnClickListener { finish() }
    }
}