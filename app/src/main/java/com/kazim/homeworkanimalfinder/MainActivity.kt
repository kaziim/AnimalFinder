package com.kazim.homeworkanimalfinder

import android.content.Context
import android.content.Intent
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

    //Text to speech
    lateinit var mTTS:TextToSpeech


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


        //---------------------------------------------------------------------------------------------------------------------------------

        mTTS = TextToSpeech(applicationContext, TextToSpeech.OnInitListener { status ->
            if(status != TextToSpeech.ERROR){
                //if there is no error
                mTTS.language = Locale.UK
            }

        })



    }

    fun buttonstart_onclick(view: View) {
        val intent = Intent(this, Level1::class.java)
        startActivity(intent)
    }


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

    fun buttontts_onclick(view: View) {
        val toSpeak = "Hey hey I'm here!"
        mTTS.speak(toSpeak,TextToSpeech.QUEUE_FLUSH,null)
        Thread.sleep(1_000)
        mTTS.stop()


    }

}