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


class Level2 : AppCompatActivity() {

    private lateinit var myDialog: Dialog
    private lateinit var btn: Button
    private lateinit var txt: TextView
    private lateinit var menuBtn: Button
    private lateinit var popImageIcon : ImageView
    private var levelCounter = 0


    class Question3d(id: Int,name1: String,name2: String,val name3: String,image1: Int,image2: Int,val image3: Int ,) :
            Question(id, name1, name2, image1, image2)

    val q1 = Question3d(1, "bird", "bear","lion", R.drawable.bird, R.drawable.bear,R.drawable.lion)
    val q2 = Question3d(1, "dog", "dolphin","duck", R.drawable.dog, R.drawable.dolphin,R.drawable.duck)
    val q3 = Question3d(1, "chicken", "monkey","mouse", R.drawable.chicken, R.drawable.monke,R.drawable.mouse)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level2)

        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)

        val Image1 = findViewById<ImageView>(R.id.image1)
        val Image2 = findViewById<ImageView>(R.id.image2)
        val Image3 = findViewById<ImageView>(R.id.image3)


        val textView = findViewById(R.id.textView2) as TextView

        if (sharedPreferences.getInt("CURRENT_QUESTION", 0) == 4) { // bird bear *lion
            textView.text = "FIND THE LION"

            Image1.setImageResource(q1.image1)
            Image2.setImageResource(q1.image2)
            Image3.setImageResource(q1.image3)

            levelCounter = 1

        }
        if (sharedPreferences.getInt("CURRENT_QUESTION", 0) == 5) { // dog *dolphin duck
            textView.text = "FIND THE DOLPHIN"

            Image1.setImageResource(q2.image1)
            Image2.setImageResource(q2.image2)
            Image3.setImageResource(q2.image3)

            levelCounter = 2

        }
        if (sharedPreferences.getInt("CURRENT_QUESTION", 0) == 6) { // *chicken monkey mouse
            textView.text = "FIND THE CHICKEN"

            Image1.setImageResource(q3.image1)
            Image2.setImageResource(q3.image2)
            Image3.setImageResource(q3.image3)

            levelCounter = 3

        }

        Toast.makeText(this, "level 2 current = ${sharedPreferences.getInt("CURRENT_QUESTION",0)}", Toast.LENGTH_SHORT).show()


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

    private fun restartThis() { //RESTARTS CURRENT ACTIVITY
        finish()
        overridePendingTransition(0, 0)
        startActivity(intent)
        overridePendingTransition(0, 0)
    }

    fun image1OnClick(view: View) {

        if (levelCounter == 1) {
            Toast.makeText(this@Level2, "Wrong Choice!.", Toast.LENGTH_SHORT).show()
        }
        if (levelCounter == 2) {
            Toast.makeText(this@Level2, "Wrong Choice!.", Toast.LENGTH_SHORT).show()
        }
        if (levelCounter == 3) {
            ShowDialog()
        }

    }

    fun image2OnClick(view: View) {
        if (levelCounter == 1) {
            Toast.makeText(this@Level2, "Wrong Choice!.", Toast.LENGTH_SHORT).show()
        }
        if (levelCounter == 2) {
            ShowDialog()
        }
        if (levelCounter == 3) {
            Toast.makeText(this@Level2, "Wrong Choice!.", Toast.LENGTH_SHORT).show()
        }
    }

    fun image3OnClick(view: View) {
        if (levelCounter == 1) {
            ShowDialog()
        }
        if (levelCounter == 2) {
            Toast.makeText(this@Level2, "Wrong Choice!.", Toast.LENGTH_SHORT).show()
        }
        if (levelCounter == 3) {
            Toast.makeText(this@Level2, "Wrong Choice!.", Toast.LENGTH_SHORT).show()
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

