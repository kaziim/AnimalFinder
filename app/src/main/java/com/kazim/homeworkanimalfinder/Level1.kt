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
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import kotlin.concurrent.timer


open class Question(
    val id: Int,
    val name1: String,
    val name2: String,
    val image1: Int,
    val image2: Int
)


class Level1 : AppCompatActivity(),TextToSpeech.OnInitListener {

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
    private var clickedCorrect = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level1)


        //Set the level
        FillLevel()

        mTTS = TextToSpeech(this,this)


    }

    override fun onResume() {
        super.onResume()
        //Start countdown timer
        val timer = object : CountDownTimer(21000, 1000) {
            val view_timer = findViewById<TextView>(R.id.view_timer)

            override fun onTick(millisUntilFinished: Long) {
                view_timer.setText("${millisUntilFinished / 1000}")
            }

            override fun onFinish() {
                view_timer.setText("0")
                timerStopped = true
                if(!isFinishing)
                {
                    mTTS.speak("No time left, try again.", TextToSpeech.QUEUE_FLUSH, null, "")
                    ShowWrongDialog()
                }
            }

        }.start()
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
                if (sharedPreferences.getInt("CURRENT_QUESTION", 0) == 1){
                    speakOut("cat",R.raw.cat)
                }
                if (sharedPreferences.getInt("CURRENT_QUESTION", 0) == 2){
                    speakOut("sheep",R.raw.sheep)
                }
                if (sharedPreferences.getInt("CURRENT_QUESTION", 0) == 3){
                    speakOut("bee",R.raw.bee)
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
        scoreCounter.text = "SCORE \n ${sharedPreferences.getInt("SCORE_COUNTER", 0)}"

        val Image1 = findViewById<ImageView>(R.id.image1)
        val Image2 = findViewById<ImageView>(R.id.image2)


        val textView = findViewById(R.id.textView2) as TextView

        val q1 = Question(1, "cat", "dog", R.drawable.cat, R.drawable.dog)
        val q2 = Question(2, "eagle", "sheep", R.drawable.eagle, R.drawable.sheep)
        val q3 = Question(3, "bee", "wolf", R.drawable.bee, R.drawable.wolf)


        if (sharedPreferences.getInt("CURRENT_QUESTION", 0) == 1) { // cat dog
            textView.text = "FIND THE \n CAT"

            Image1.setImageResource(q1.image1)
            Image2.setImageResource(q1.image2)

            levelCounter = 1


        }
        if (sharedPreferences.getInt("CURRENT_QUESTION", 0) == 2) { // eagle sheep
            textView.text = "FIND THE \n SHEEP"

            Image1.setImageResource(q2.image1)
            Image2.setImageResource(q2.image2)

            levelCounter = 2


        }
        if (sharedPreferences.getInt("CURRENT_QUESTION", 0) == 3) { // bee wolf
            textView.text = "FIND THE \n BEE"

            Image1.setImageResource(q3.image1)
            Image2.setImageResource(q3.image2)

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
        val temp = sharedPreferences.getInt("CURRENT_QUESTION", 0)

        if (temp == 3){
            txt.text = "YOU COMPLETED LEVEL 1 "
            btn.text = "NEXT LEVEL"
        }

        btn.setOnClickListener {

            if (temp < 3){
                editor.putInt(
                    "CURRENT_QUESTION",
                    sharedPreferences.getInt("CURRENT_QUESTION", 0) + 1
                )
                editor.commit()

                myDialog.cancel()

                //this.recreate()
                restartThis()
            }
            else if(temp == 3){
                Log.d("catistan", "temp3")
                editor.putInt(
                    "CURRENT_QUESTION",
                    sharedPreferences.getInt("CURRENT_QUESTION", 0) + 1
                )
                editor.commit()

                val intent = Intent(this, Level2::class.java)
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

        if (timerStopped){
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


    fun ScoreCheck(){
        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        //Increase current score
        editor.putInt("SCORE_COUNTER", sharedPreferences.getInt("SCORE_COUNTER", 0) + 1)
        editor.commit()

        //Check if its personal best score
        val current = sharedPreferences.getInt("SCORE_COUNTER", 0)
        val best = sharedPreferences.getInt("PERSONAL_BEST", 0)
        if(current > best){
            editor.putInt("PERSONAL_BEST", current)
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

