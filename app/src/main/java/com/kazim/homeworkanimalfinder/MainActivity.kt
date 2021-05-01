package com.kazim.homeworkanimalfinder

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.media.SoundPool
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.speech.tts.TextToSpeech
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var myDialog: Dialog
    private lateinit var settingsButton: Button


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

    fun howButtonOnClick(view: View) {
        myDialog = Dialog(this)
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        myDialog.setContentView(R.layout.activity_how_to_play)

        myDialog.show()
    }
    fun exitButtonOnClick(view: View) {
        finishAffinity()
    }

    private fun restartThis() { //RESTARTS CURRENT ACTIVITY
        finish()
        overridePendingTransition(0, 0)
        startActivity(intent)
        overridePendingTransition(0, 0)
    }

    fun settingsButtonOnClick(view: View) {
        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        myDialog = Dialog(this)
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        myDialog.setContentView(R.layout.activity_settings)

        settingsButton = myDialog.findViewById(R.id.resetScoreButton) as Button
        settingsButton.setOnClickListener{
            editor.putInt("PERSONAL_BEST",0)
            editor.commit()
            myDialog.cancel()
            Handler().postDelayed({
                restartThis()
            },1000)



        }


        myDialog.show()

    }

}