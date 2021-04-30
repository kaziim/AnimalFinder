package com.kazim.homeworkanimalfinder

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

open class Question(val id: Int, val name1: String, val name2: String, val image1: Int, val image2: Int)


class Level1 : AppCompatActivity() {

    private lateinit var myDialog: Dialog
    private lateinit var btn: Button
    private lateinit var txt: TextView
    private lateinit var menuBtn: Button
    private lateinit var popImageIcon : ImageView
    private var levelCounter = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level1)

        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)


        val Image1 = findViewById<ImageView>(R.id.image1)
        val Image2 = findViewById<ImageView>(R.id.image2)


        val textView = findViewById(R.id.textView2) as TextView

        val q1 = Question(1, "cat", "dog", R.drawable.cat, R.drawable.dog)
        val q2 = Question(2, "eagle", "sheep", R.drawable.eagle, R.drawable.sheep)
        val q3 = Question(3, "bee", "wolf", R.drawable.bee, R.drawable.wolf)


        if (sharedPreferences.getInt("CURRENT_QUESTION", 0) == 1) { // cat dog
            textView.text = "FIND THE CAT"

            Image1.setImageResource(q1.image1)
            Image2.setImageResource(q1.image2)

            levelCounter = 1

        }
        if (sharedPreferences.getInt("CURRENT_QUESTION", 0) == 2) { // eagle sheep
            textView.text = "FIND THE SHEEP"

            Image1.setImageResource(q2.image1)
            Image2.setImageResource(q2.image2)

            levelCounter = 2

        }
        if (sharedPreferences.getInt("CURRENT_QUESTION", 0) == 3) { // bee wolf
            textView.text = "FIND THE BEE"

            Image1.setImageResource(q3.image1)
            Image2.setImageResource(q3.image2)

            levelCounter = 3

        }

        Toast.makeText(this, "current = ${sharedPreferences.getInt("CURRENT_QUESTION",0)}", Toast.LENGTH_SHORT).show()



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

        if (temp == 3){
            txt.text = "YOU COMPLETED LEVEL 1 "
            btn.text = "NEXT LEVEL"
        }

        btn.setOnClickListener {

            if (temp < 3){
                editor.putInt("CURRENT_QUESTION", sharedPreferences.getInt("CURRENT_QUESTION", 0) + 1)
                editor.commit()

                myDialog.cancel()

                //this.recreate()
                restartThis()
            }
            else if(temp == 3){
                Log.d("catistan","temp3")
                editor.putInt("CURRENT_QUESTION", sharedPreferences.getInt("CURRENT_QUESTION", 0) + 1)
                editor.commit()

                val intent = Intent(this,Level2::class.java)
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
        Log.d("catistan", "onclick tiklandi")


        if (levelCounter == 1) {
            Toast.makeText(this@Level1, "You did it.", Toast.LENGTH_SHORT).show()

            ShowDialog()
        }
        if (levelCounter == 2) {
            Toast.makeText(this@Level1, "Wrong Choice!.", Toast.LENGTH_SHORT).show()
        }
        if (levelCounter == 3) {
            Toast.makeText(this@Level1, "You did it.", Toast.LENGTH_SHORT).show()

            ShowDialog()
        }

    }

    fun image2OnClick(view: View) {
        if (levelCounter == 1) {
            Toast.makeText(this@Level1, "Wrong Choice!.", Toast.LENGTH_SHORT).show()
        }
        if (levelCounter == 2) {
            Toast.makeText(this@Level1, "You did it.", Toast.LENGTH_SHORT).show()

            ShowDialog()
        }
        if (levelCounter == 3) {
            Toast.makeText(this@Level1, "Wrong Choice!.", Toast.LENGTH_SHORT).show()
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

