package com.kazim.homeworkanimalfinder

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*


class Level2 : AppCompatActivity(),TextToSpeech.OnInitListener {

    private lateinit var myDialog: Dialog
    private lateinit var btn: Button
    private lateinit var txt: TextView
    private lateinit var txtScore: TextView
    private lateinit var menuBtn: Button
    private lateinit var popImageIcon : ImageView
    private var levelCounter = 0

    //Text to speech
    lateinit var mTTS: TextToSpeech
    private var mp: MediaPlayer? = null
    private var timerStopped = false

    private var isInFront = false


    class Question3d(id: Int,name1: String,name2: String,val name3: String,image1: Int,image2: Int,val image3: Int ,) :
            Question(id, name1, name2, image1, image2)

    val q1 = Question3d(1, "bird", "bear","lion", R.drawable.bird, R.drawable.bear,R.drawable.lion)
    val q2 = Question3d(1, "dog", "dolphin","duck", R.drawable.dog, R.drawable.dolphin,R.drawable.duck)
    val q3 = Question3d(1, "chicken", "monkey","mouse", R.drawable.chicken, R.drawable.monke,R.drawable.mouse)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level2)


        FillLevel()

        mTTS = TextToSpeech(this,this)


    }

    override fun onResume() {
        super.onResume()
        isInFront = true
        //Start countdown timer
        val timer = object : CountDownTimer(18000, 1000) {
            val view_timer = findViewById<TextView>(R.id.view_timer)

            override fun onTick(millisUntilFinished: Long) {
                view_timer.setText("${millisUntilFinished / 1000}")
            }

            override fun onFinish() {
                view_timer.setText("0")
                timerStopped = true
                if(!isFinishing())
                {
                    ShowWrongDialog()
                }

            }

        }.start()

    }

    override fun onPause() {
        super.onPause()
        isInFront = false
    }

    override fun onDestroy() {
        super.onDestroy()
        mTTS.stop()
        mTTS.shutdown()
        mp?.stop()
        mp?.reset()
        mp?.release()
    }


    override fun onInit(status: Int) {
        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        if (status == TextToSpeech.SUCCESS) {
            val result = mTTS.setLanguage(Locale.UK)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.i("catistan", "The Language specified is not supported!")
            } else {
                if (sharedPreferences.getInt("CURRENT_QUESTION", 0) == 4){
                    speakOut("lion",R.raw.lion)
                }
                if (sharedPreferences.getInt("CURRENT_QUESTION", 0) == 5){
                    speakOut("dolphin",R.raw.dolphin)
                }
                if (sharedPreferences.getInt("CURRENT_QUESTION", 0) == 6){
                    speakOut("chicken",R.raw.chicken)
                }
            }
        } else Log.i("catistan", "Initialization Failed!")
    }

    fun speakOut(name: String, resource: Int){
        val toSpeak = "$name ,A $name sounds like "
        mTTS.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null, "")
        Handler().postDelayed({
            if (mp == null){

                mp = MediaPlayer.create(this, resource)
                mp?.start()
            }
        },2000)

    }

    fun speakOutNoMP(flag: Boolean){
        var toSpeak : String
        if (flag){
            toSpeak = "You got it! "
        }else{
            toSpeak = "Wrong Answer, Try again."
        }

        mTTS.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null, "")

    }

    fun FillLevel(){
        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)

        //Put score on the score counter
        val scoreCounter = findViewById<TextView>(R.id.scoreText)
        scoreCounter.text = "SCORE : ${sharedPreferences.getInt("SCORE_COUNTER",0)}"

        val Image1 = findViewById<ImageView>(R.id.image1)
        val Image2 = findViewById<ImageView>(R.id.image2)
        val Image3 = findViewById<ImageView>(R.id.image3)


        val textView = findViewById(R.id.textView2) as TextView

        if (sharedPreferences.getInt("CURRENT_QUESTION", 0) == 4) { // bird bear *lion
            textView.text = "FIND THE \n LION"

            Image1.setImageResource(q1.image1)
            Image2.setImageResource(q1.image2)
            Image3.setImageResource(q1.image3)

            levelCounter = 1

        }
        if (sharedPreferences.getInt("CURRENT_QUESTION", 0) == 5) { // dog *dolphin duck
            textView.text = "FIND THE \n DOLPHIN"

            Image1.setImageResource(q2.image1)
            Image2.setImageResource(q2.image2)
            Image3.setImageResource(q2.image3)

            levelCounter = 2

        }
        if (sharedPreferences.getInt("CURRENT_QUESTION", 0) == 6) { // *chicken monkey mouse
            textView.text = "FIND THE \n CHICKEN"

            Image1.setImageResource(q3.image1)
            Image2.setImageResource(q3.image2)
            Image3.setImageResource(q3.image3)

            levelCounter = 3

        }
    }

    fun ShowDialog() {
        myDialog = Dialog(this)
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        myDialog.setContentView(R.layout.activity_correct_answer)

        btn = myDialog.findViewById(R.id.nextButton) as Button
        txt = myDialog.findViewById(R.id.messageText) as TextView

        popImageIcon = myDialog.findViewById(R.id.popImageIcon) as ImageView
        popImageIcon.setImageResource(R.drawable.happy)

        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val temp = sharedPreferences.getInt("CURRENT_QUESTION",0)

        if (temp == 6){
            txt.text = "YOU COMPLETED LEVEL 2 "
            btn.text = "NEXT LEVEL"
        }

        btn.setOnClickListener {

            if (temp < 6){
                editor.putInt("CURRENT_QUESTION", sharedPreferences.getInt("CURRENT_QUESTION", 0) + 1)
                editor.commit()

                myDialog.cancel()

                //this.recreate()
                restartThis()
            }
            else if(temp == 6){
                editor.putInt("CURRENT_QUESTION", sharedPreferences.getInt("CURRENT_QUESTION", 0) + 1)
                editor.commit()

                myDialog.cancel()

                finish()
                val intent = Intent(this,Level3::class.java)
                startActivity(intent)
            }

        }

        menuBtn = myDialog.findViewById(R.id.exitButton) as Button
        menuBtn.setOnClickListener{
            myDialog.cancel()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        myDialog.show()

    }

    fun ShowWrongDialog() {
        myDialog = Dialog(this)
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        myDialog.setContentView(R.layout.activity_wrong_answer)
        btn = myDialog.findViewById(R.id.tryButton) as Button
        txt = myDialog.findViewById(R.id.messageText) as TextView
        txtScore = myDialog.findViewById(R.id.totalScoreText) as TextView

        popImageIcon = myDialog.findViewById(R.id.popImageIcon) as ImageView
        popImageIcon.setImageResource(R.drawable.sad)

        if (timerStopped && isInFront){
            mTTS.speak("No time left, try again.", TextToSpeech.QUEUE_FLUSH, null, "")
            txt.text = "NO TIME LEFT "
        }

        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        txtScore.text = "Your Score : ${sharedPreferences.getInt("SCORE_COUNTER", 0)}"

        btn.setOnClickListener {


            editor.putInt("CURRENT_QUESTION", 1)
            editor.putInt("SCORE_COUNTER", 0)
            editor.commit()

            myDialog.cancel()

            finish()
            val intent = Intent(this, Level1::class.java)
            startActivity(intent)
        }

        menuBtn = myDialog.findViewById(R.id.exitButton) as Button
        menuBtn.setOnClickListener{
            myDialog.cancel()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        myDialog.show()

    }

    private fun restartThis() { //RESTARTS CURRENT ACTIVITY
        finish()
        overridePendingTransition(0, 0)
        startActivity(intent)
        overridePendingTransition(0, 0)
    }

    fun image1OnClick(view: View) {
        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        if (levelCounter == 1) {
            speakOutNoMP(false)
            ShowWrongDialog()
        }
        if (levelCounter == 2) {
            speakOutNoMP(false)
            ShowWrongDialog()
        }
        if (levelCounter == 3) {
            speakOutNoMP(true)
            ScoreCheck()
            ShowDialog()
        }

    }

    fun image2OnClick(view: View) {
        if (levelCounter == 1) {
            speakOutNoMP(false)
            ShowWrongDialog()
        }
        if (levelCounter == 2) {
            speakOutNoMP(true)
            ScoreCheck()
            ShowDialog()
        }
        if (levelCounter == 3) {
            speakOutNoMP(false)
            ShowWrongDialog()
        }
    }

    fun image3OnClick(view: View) {
        if (levelCounter == 1) {
            speakOutNoMP(true)
            ScoreCheck()
            ShowDialog()
        }
        if (levelCounter == 2) {
            speakOutNoMP(false)
            ShowWrongDialog()
        }
        if (levelCounter == 3) {
            speakOutNoMP(false)
            ShowWrongDialog()
        }
    }

    fun ScoreCheck(){
        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        //Increase current score
        editor.putInt("SCORE_COUNTER", sharedPreferences.getInt("SCORE_COUNTER", 0) + 2)
        editor.commit()

        //Check if its personal best score
        val current = sharedPreferences.getInt("SCORE_COUNTER",0)
        val best = sharedPreferences.getInt("PERSONAL_BEST",0)
        if(current > best){
            editor.putInt("PERSONAL_BEST",current)
            editor.commit()
        }
    }


    //----------------------------------------------------------------------------------------------------------------------------------------------------

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

