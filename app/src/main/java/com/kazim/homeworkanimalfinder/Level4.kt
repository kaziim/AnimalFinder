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


class Level4 : AppCompatActivity() {

    private lateinit var myDialog: Dialog
    private lateinit var btn: Button
    private lateinit var txt: TextView
    private lateinit var menuBtn: Button
    private lateinit var popImageIcon : ImageView
    private var levelCounter = 0


    class Question6d(id: Int,name1: String,name2: String,val name3: String,val name4: String,val name5: String,val name6: String,
                     image1: Int,image2: Int,val image3: Int ,val image4: Int,val image5: Int ,val image6: Int) :
        Question(id, name1, name2, image1, image2)

    val q1 = Question6d(1, "bear", "duck","dog","mouse","sheep","dolphin",
        R.drawable.bear, R.drawable.duck,R.drawable.dog,R.drawable.mouse,R.drawable.sheep,R.drawable.dolphin)
    val q2 = Question6d(1, "monke", "cow","cat","bird","lion","horse",
        R.drawable.monke, R.drawable.cow,R.drawable.cat,R.drawable.bird,R.drawable.lion,R.drawable.horse)
    val q3 = Question6d(1, "owl", "pig","horse","rooster","wolf","eagle",
        R.drawable.owl, R.drawable.pig,R.drawable.horse,R.drawable.rooster,R.drawable.wolf,R.drawable.eagle)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level4)


        FillLevel()

    }

    fun FillLevel(){
        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)

        val Image1 = findViewById<ImageView>(R.id.image1)
        val Image2 = findViewById<ImageView>(R.id.image2)
        val Image3 = findViewById<ImageView>(R.id.image3)
        val Image4 = findViewById<ImageView>(R.id.image4)
        val Image5 = findViewById<ImageView>(R.id.image5)
        val Image6 = findViewById<ImageView>(R.id.image6)


        val textView = findViewById(R.id.textView2) as TextView

        if (sharedPreferences.getInt("CURRENT_QUESTION", 0) == 10) { // bear *duck dog mouse sheep dolphin
            textView.text = "FIND THE DUCK"

            Image1.setImageResource(q1.image1)
            Image2.setImageResource(q1.image2)
            Image3.setImageResource(q1.image3)
            Image4.setImageResource(q1.image4)
            Image5.setImageResource(q1.image5)
            Image6.setImageResource(q1.image6)

            levelCounter = 1

        }
        if (sharedPreferences.getInt("CURRENT_QUESTION", 0) == 11) { // monkey cow cat bird *lion horse
            textView.text = "FIND THE LION"

            Image1.setImageResource(q2.image1)
            Image2.setImageResource(q2.image2)
            Image3.setImageResource(q2.image3)
            Image4.setImageResource(q2.image4)
            Image5.setImageResource(q2.image5)
            Image6.setImageResource(q2.image6)

            levelCounter = 2

        }
        if (sharedPreferences.getInt("CURRENT_QUESTION", 0) == 12) { // owl pig horse *rooster wolf eagle
            textView.text = "FIND THE ROOSTER"

            Image1.setImageResource(q3.image1)
            Image2.setImageResource(q3.image2)
            Image3.setImageResource(q3.image3)
            Image4.setImageResource(q3.image4)
            Image5.setImageResource(q3.image5)
            Image6.setImageResource(q3.image6)

            levelCounter = 3

        }
        Toast.makeText(this, "level 4 current = ${sharedPreferences.getInt("CURRENT_QUESTION",0)}", Toast.LENGTH_SHORT).show()
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

        if (temp == 12){
            txt.text = "YOU COMPLETED LEVEL 4 "
            btn.text = "NEXT LEVEL"
        }

        btn.setOnClickListener {

            if (temp < 12){
                editor.putInt("CURRENT_QUESTION", sharedPreferences.getInt("CURRENT_QUESTION", 0) + 1)
                editor.commit()

                myDialog.cancel()

                //this.recreate()
                restartThis()
            }
            else if(temp == 12){
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
            Toast.makeText(this@Level4, "Wrong Choice!.", Toast.LENGTH_SHORT).show()
        }
        if (levelCounter == 2) {
            Toast.makeText(this@Level4, "Wrong Choice!.", Toast.LENGTH_SHORT).show()
        }
        if (levelCounter == 3) {
            Toast.makeText(this@Level4, "Wrong Choice!.", Toast.LENGTH_SHORT).show()
        }

    }

    fun image2OnClick(view: View) {
        if (levelCounter == 1) {
            ShowDialog()
        }
        if (levelCounter == 2) {
            Toast.makeText(this@Level4, "Wrong Choice!.", Toast.LENGTH_SHORT).show()
        }
        if (levelCounter == 3) {
            Toast.makeText(this@Level4, "Wrong Choice!.", Toast.LENGTH_SHORT).show()
        }
    }

    fun image3OnClick(view: View) {
        if (levelCounter == 1) {
            Toast.makeText(this@Level4, "Wrong Choice!.", Toast.LENGTH_SHORT).show()
        }
        if (levelCounter == 2) {
            Toast.makeText(this@Level4, "Wrong Choice!.", Toast.LENGTH_SHORT).show()
        }
        if (levelCounter == 3) {
            Toast.makeText(this@Level4, "Wrong Choice!.", Toast.LENGTH_SHORT).show()
        }
    }

    fun image4OnClick(view: View) {
        if (levelCounter == 1) {
            Toast.makeText(this@Level4, "Wrong Choice!.", Toast.LENGTH_SHORT).show()
        }
        if (levelCounter == 2) {
            Toast.makeText(this@Level4, "Wrong Choice!.", Toast.LENGTH_SHORT).show()
        }
        if (levelCounter == 3) {
            ShowDialog()
        }
    }

    fun image5OnClick(view: View) {
        if (levelCounter == 1) {
            Toast.makeText(this@Level4, "Wrong Choice!.", Toast.LENGTH_SHORT).show()
        }
        if (levelCounter == 2) {
            ShowDialog()
        }
        if (levelCounter == 3) {
            Toast.makeText(this@Level4, "Wrong Choice!.", Toast.LENGTH_SHORT).show()
        }
    }

    fun image6OnClick(view: View) {
        if (levelCounter == 1) {
            Toast.makeText(this@Level4, "Wrong Choice!.", Toast.LENGTH_SHORT).show()
        }
        if (levelCounter == 2) {
            Toast.makeText(this@Level4, "Wrong Choice!.", Toast.LENGTH_SHORT).show()
        }
        if (levelCounter == 3) {
            Toast.makeText(this@Level4, "Wrong Choice!.", Toast.LENGTH_SHORT).show()
        }
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




}

