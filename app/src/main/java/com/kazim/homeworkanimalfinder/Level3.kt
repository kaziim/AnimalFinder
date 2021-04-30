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


class Level3 : AppCompatActivity() {

    private lateinit var myDialog: Dialog
    private lateinit var btn: Button
    private lateinit var txt: TextView
    private lateinit var menuBtn: Button
    private lateinit var popImageIcon : ImageView
    private var levelCounter = 0


    class Question4d(id: Int,name1: String,name2: String,val name3: String,val name4: String,image1: Int,image2: Int,val image3: Int ,val image4: Int) :
            Question(id, name1, name2, image1, image2)

    val q1 = Question4d(1, "pig", "rooster","horse","owl", R.drawable.pig, R.drawable.rooster,R.drawable.horse,R.drawable.owl)
    val q2 = Question4d(1, "elephant", "mouse","bird","monkey", R.drawable.elephant, R.drawable.mouse,R.drawable.bird,R.drawable.monke)
    val q3 = Question4d(1, "eagle", "cat","wolf","sheep", R.drawable.eagle, R.drawable.cat,R.drawable.wolf,R.drawable.sheep)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level3)


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


        val textView = findViewById(R.id.textView2) as TextView

        if (sharedPreferences.getInt("CURRENT_QUESTION", 0) == 7) { // pig rooster *horse owl
            textView.text = "FIND THE HORSE"

            Image1.setImageResource(q1.image1)
            Image2.setImageResource(q1.image2)
            Image3.setImageResource(q1.image3)
            Image4.setImageResource(q1.image4)

            levelCounter = 1

        }
        if (sharedPreferences.getInt("CURRENT_QUESTION", 0) == 8) { // *elephant mouse bird monkey
            textView.text = "FIND THE ELEPHANT"

            Image1.setImageResource(q2.image1)
            Image2.setImageResource(q2.image2)
            Image3.setImageResource(q2.image3)
            Image4.setImageResource(q2.image4)

            levelCounter = 2

        }
        if (sharedPreferences.getInt("CURRENT_QUESTION", 0) == 9) { // eagle cat *wolf sheep
            textView.text = "FIND THE WOLF"

            Image1.setImageResource(q3.image1)
            Image2.setImageResource(q3.image2)
            Image3.setImageResource(q3.image3)
            Image4.setImageResource(q3.image4)

            levelCounter = 3

        }

        Toast.makeText(this, "level 3 current = ${sharedPreferences.getInt("CURRENT_QUESTION",0)}", Toast.LENGTH_SHORT).show()
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

        if (temp == 9){
            txt.text = "YOU COMPLETED LEVEL 3 "
            btn.text = "NEXT LEVEL"
        }

        btn.setOnClickListener {

            if (temp < 9){
                editor.putInt("CURRENT_QUESTION", sharedPreferences.getInt("CURRENT_QUESTION", 0) + 1)
                editor.commit()

                myDialog.cancel()

                //this.recreate()
                restartThis()
            }
            else if(temp == 9){
                editor.putInt("CURRENT_QUESTION", sharedPreferences.getInt("CURRENT_QUESTION", 0) + 1)
                editor.commit()

                myDialog.cancel()
                val intent = Intent(this,Level4::class.java)
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
            Toast.makeText(this@Level3, "Wrong Choice!.", Toast.LENGTH_SHORT).show()
        }
        if (levelCounter == 2) {
            ScoreCheck()
            ShowDialog()
        }
        if (levelCounter == 3) {
            Toast.makeText(this@Level3, "Wrong Choice!.", Toast.LENGTH_SHORT).show()
        }

    }

    fun image2OnClick(view: View) {
        if (levelCounter == 1) {
            Toast.makeText(this@Level3, "Wrong Choice!.", Toast.LENGTH_SHORT).show()
        }
        if (levelCounter == 2) {
            Toast.makeText(this@Level3, "Wrong Choice!.", Toast.LENGTH_SHORT).show()
        }
        if (levelCounter == 3) {
            Toast.makeText(this@Level3, "Wrong Choice!.", Toast.LENGTH_SHORT).show()
        }
    }

    fun image3OnClick(view: View) {
        if (levelCounter == 1) {
            ScoreCheck()
            ShowDialog()
        }
        if (levelCounter == 2) {
            Toast.makeText(this@Level3, "Wrong Choice!.", Toast.LENGTH_SHORT).show()
        }
        if (levelCounter == 3) {
            ScoreCheck()
            ShowDialog()
        }
    }

    fun image4OnClick(view: View) {
        if (levelCounter == 1) {
            Toast.makeText(this@Level3, "Wrong Choice!.", Toast.LENGTH_SHORT).show()
        }
        if (levelCounter == 2) {
            Toast.makeText(this@Level3, "Wrong Choice!.", Toast.LENGTH_SHORT).show()
        }
        if (levelCounter == 3) {
            Toast.makeText(this@Level3, "Wrong Choice!.", Toast.LENGTH_SHORT).show()
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

