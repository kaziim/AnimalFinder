package com.kazim.homeworkanimalfinder

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class Level5 : AppCompatActivity() {

    private lateinit var myDialog: Dialog
    private lateinit var btn: Button
    private lateinit var txt: TextView
    private lateinit var menuBtn: Button
    private lateinit var popImageIcon : ImageView
    private var levelCounter = 0


    class Question8d(id: Int,name1: String,name2: String,val name3: String,val name4: String,val name5: String,val name6: String,val name7: String,val name8: String,
                     image1: Int,image2: Int,val image3: Int ,val image4: Int,val image5: Int ,val image6: Int,val image7: Int ,val image8: Int) :
        Question(id, name1, name2, image1, image2)

    val q1 = Question8d(1, "cat", "horse","pig","sheep","eagle","bird","bee","dog",
        R.drawable.cat, R.drawable.horse,R.drawable.pig,R.drawable.sheep,R.drawable.eagle,R.drawable.bird,R.drawable.bee,R.drawable.dog)
    val q2 = Question8d(1, "lion", "monke","owl","mouse","cow","duck","chicken","elephant",
        R.drawable.lion, R.drawable.monke,R.drawable.owl,R.drawable.mouse,R.drawable.cow,R.drawable.duck,R.drawable.chicken,R.drawable.elephant)
    val q3 = Question8d(1, "bear", "bee","bird","wolf","sheep","dolphin","horse","lion",
        R.drawable.bear, R.drawable.bee,R.drawable.bird,R.drawable.wolf,R.drawable.sheep,R.drawable.dolphin,R.drawable.horse,R.drawable.lion)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level5)

        FillLevel()
    }

    fun FillLevel(){
        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)

        //Put score on the score counter
        val scoreCounter = findViewById<TextView>(R.id.scoreText)
        scoreCounter.text = "SCORE : ${sharedPreferences.getInt("SCORE_COUNTER",0)}"

        val Image1 = findViewById<ImageView>(R.id.image1)
        val Image2 = findViewById<ImageView>(R.id.image2)
        val Image3 = findViewById<ImageView>(R.id.image3)
        val Image4 = findViewById<ImageView>(R.id.image4)
        val Image5 = findViewById<ImageView>(R.id.image5)
        val Image6 = findViewById<ImageView>(R.id.image6)
        val Image7 = findViewById<ImageView>(R.id.image7)
        val Image8 = findViewById<ImageView>(R.id.image8)


        val textView = findViewById(R.id.textView2) as TextView

        if (sharedPreferences.getInt("CURRENT_QUESTION", 0) == 13) { // cat horse pig sheep eagle *bird bee dog
            textView.text = "FIND THE PARROT"

            Image1.setImageResource(q1.image1)
            Image2.setImageResource(q1.image2)
            Image3.setImageResource(q1.image3)
            Image4.setImageResource(q1.image4)
            Image5.setImageResource(q1.image5)
            Image6.setImageResource(q1.image6)
            Image7.setImageResource(q1.image7)
            Image8.setImageResource(q1.image8)

            levelCounter = 1

        }
        if (sharedPreferences.getInt("CURRENT_QUESTION", 0) == 14) { // lion monke *owl mouse cow duck chicken elephant
            textView.text = "FIND THE OWL"

            Image1.setImageResource(q2.image1)
            Image2.setImageResource(q2.image2)
            Image3.setImageResource(q2.image3)
            Image4.setImageResource(q2.image4)
            Image5.setImageResource(q2.image5)
            Image6.setImageResource(q2.image6)
            Image7.setImageResource(q2.image7)
            Image8.setImageResource(q2.image8)

            levelCounter = 2

        }
        if (sharedPreferences.getInt("CURRENT_QUESTION", 0) == 15) { // *bear bee bird wolf sheep dolphin horse lion
            textView.text = "FIND THE BEAR"

            Image1.setImageResource(q3.image1)
            Image2.setImageResource(q3.image2)
            Image3.setImageResource(q3.image3)
            Image4.setImageResource(q3.image4)
            Image5.setImageResource(q3.image5)
            Image6.setImageResource(q3.image6)
            Image7.setImageResource(q3.image7)
            Image8.setImageResource(q3.image8)

            levelCounter = 3

        }
        Toast.makeText(this, "level 5 current = ${sharedPreferences.getInt("CURRENT_QUESTION",0)}", Toast.LENGTH_SHORT).show()
    }

    fun ShowDialog() {
        myDialog = Dialog(this)
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        myDialog.setContentView(R.layout.activity_correct_answer)

        btn = myDialog.findViewById(R.id.nextButton) as Button
        txt = myDialog.findViewById(R.id.messageText) as TextView

        popImageIcon = myDialog.findViewById(R.id.popImageIcon) as ImageView
        popImageIcon.setImageResource(R.drawable.checkmarkgreen)

        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val temp = sharedPreferences.getInt("CURRENT_QUESTION",0)

        if (temp == 15){
            txt.text = "YOU COMPLETED LEVEL 5 "
            btn.text = "NEXT LEVEL"
        }

        btn.setOnClickListener {

            if (temp < 15){
                editor.putInt("CURRENT_QUESTION", sharedPreferences.getInt("CURRENT_QUESTION", 0) + 1)
                editor.commit()

                myDialog.cancel()

                //this.recreate()
                restartThis()
            }
            else if(temp == 15){
                editor.putInt("CURRENT_QUESTION", sharedPreferences.getInt("CURRENT_QUESTION", 0) + 1)
                editor.commit()

                myDialog.cancel()
                val intent = Intent(this,MainActivity::class.java)
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

    private fun restartThis() { //RESTARTS CURRENT ACTIVITY
        finish()
        overridePendingTransition(0, 0)
        startActivity(intent)
        overridePendingTransition(0, 0)
    }

    fun image1OnClick(view: View) {

        if (levelCounter == 1) {
            Toast.makeText(this, "Wrong Choice!.", Toast.LENGTH_SHORT).show()
        }
        if (levelCounter == 2) {
            Toast.makeText(this, "Wrong Choice!.", Toast.LENGTH_SHORT).show()
        }
        if (levelCounter == 3) {
            ScoreCheck()
            ShowDialog()
        }

    }

    fun image2OnClick(view: View) {
        if (levelCounter == 1) {
            Toast.makeText(this, "Wrong Choice!.", Toast.LENGTH_SHORT).show()
        }
        if (levelCounter == 2) {
            Toast.makeText(this, "Wrong Choice!.", Toast.LENGTH_SHORT).show()
        }
        if (levelCounter == 3) {
            Toast.makeText(this, "Wrong Choice!.", Toast.LENGTH_SHORT).show()
        }
    }

    fun image3OnClick(view: View) {
        if (levelCounter == 1) {
            Toast.makeText(this, "Wrong Choice!.", Toast.LENGTH_SHORT).show()
        }
        if (levelCounter == 2) {
            ScoreCheck()
            ShowDialog()
        }
        if (levelCounter == 3) {
            Toast.makeText(this, "Wrong Choice!.", Toast.LENGTH_SHORT).show()
        }
    }

    fun image4OnClick(view: View) {
        if (levelCounter == 1) {
            Toast.makeText(this, "Wrong Choice!.", Toast.LENGTH_SHORT).show()
        }
        if (levelCounter == 2) {
            Toast.makeText(this, "Wrong Choice!.", Toast.LENGTH_SHORT).show()
        }
        if (levelCounter == 3) {
            Toast.makeText(this, "Wrong Choice!.", Toast.LENGTH_SHORT).show()
        }
    }

    fun image5OnClick(view: View) {
        if (levelCounter == 1) {
            Toast.makeText(this, "Wrong Choice!.", Toast.LENGTH_SHORT).show()
        }
        if (levelCounter == 2) {
            Toast.makeText(this, "Wrong Choice!.", Toast.LENGTH_SHORT).show()
        }
        if (levelCounter == 3) {
            Toast.makeText(this, "Wrong Choice!.", Toast.LENGTH_SHORT).show()
        }
    }

    fun image6OnClick(view: View) {
        if (levelCounter == 1) {
            ScoreCheck()
            ShowDialog()
        }
        if (levelCounter == 2) {
            Toast.makeText(this, "Wrong Choice!.", Toast.LENGTH_SHORT).show()
        }
        if (levelCounter == 3) {
            Toast.makeText(this, "Wrong Choice!.", Toast.LENGTH_SHORT).show()
        }
    }

    fun image7OnClick(view: View) {
        if (levelCounter == 1) {
            Toast.makeText(this, "Wrong Choice!.", Toast.LENGTH_SHORT).show()
        }
        if (levelCounter == 2) {
            Toast.makeText(this, "Wrong Choice!.", Toast.LENGTH_SHORT).show()
        }
        if (levelCounter == 3) {
            Toast.makeText(this, "Wrong Choice!.", Toast.LENGTH_SHORT).show()
        }
    }

    fun image8OnClick(view: View) {
        if (levelCounter == 1) {
            Toast.makeText(this, "Wrong Choice!.", Toast.LENGTH_SHORT).show()
        }
        if (levelCounter == 2) {
            Toast.makeText(this, "Wrong Choice!.", Toast.LENGTH_SHORT).show()
        }
        if (levelCounter == 3) {
            Toast.makeText(this, "Wrong Choice!.", Toast.LENGTH_SHORT).show()
        }
    }

    fun ScoreCheck(){
        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        //Increase current score
        editor.putInt("SCORE_COUNTER", sharedPreferences.getInt("SCORE_COUNTER", 0) + 1)
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
