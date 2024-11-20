package com.example.lab_exam_3

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

//Game home screen
//setup the UI and handles button clicks to start the game
class GameHome: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val option1 : Button = findViewById(R.id.option1)

        option1.setOnClickListener{
            val intent = Intent(this,GameActivity::class.java)
            startActivity(intent)
        }


    }
}