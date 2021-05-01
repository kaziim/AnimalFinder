package com.kazim.homeworkanimalfinder

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.media.SoundPool
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        //---------------------------------------------------------------------------------------------------------------------------------
        //PERSONAL BEST SCORE DISPLAY
        val scoreText = findViewById<TextView>(R.id.scoreText)
        if (sharedPreferences.contains("PERSONAL_BEST")){
            scoreText.text = "PERSONAL BEST SCORE : ${sharedPreferences.getInt("PERSONAL_BEST",0)}"
        }


        if(sharedPreferences.contains("CURRENT_QUESTION")){
            editor.putInt("CURRENT_QUESTION",1)
            editor.commit()
        }

        //Initialize current score counter
        editor.putInt("SCORE_COUNTER",0)
        editor.commit()


    }

    fun buttonstart_onclick(view: View) {
        val intent = Intent(this, Level1::class.java)
        startActivity(intent)
    }



    // Disable status bar
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        //Hide the status bar for different API levels

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.decorView.windowInsetsController!!.hide(
                    android.view.WindowInsets.Type.statusBars()
            )
        }else {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        }
    }

}