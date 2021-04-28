package com.kazim.homeworkanimalfinder

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ContentView
import androidx.appcompat.app.AlertDialog

class Question(val id: Int, val name1: String, val name2: String, val image1: Int, val image2: Int) {

}

class Level1 : AppCompatActivity() {

    private lateinit var myDialog: Dialog
    private lateinit var btn: Button
    private var levelCounter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level1)

        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)


        val Image1 = findViewById<ImageView>(R.id.image1)
        val Image2 = findViewById<ImageView>(R.id.image2)
        Image1.bringToFront()
        Image2.bringToFront()

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


        Image1.setOnClickListener {

        }

    }

    fun ShowDialog() {
        myDialog = Dialog(this)
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        myDialog.setContentView(R.layout.activity_correct_answer)
        myDialog.setTitle("YOU DID IT!")

        btn = myDialog.findViewById(R.id.nextButton) as Button
        btn.setOnClickListener {
            val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putInt("CURRENT_QUESTION", sharedPreferences.getInt("CURRENT_QUESTION", 0) + 1)
            editor.commit()

            myDialog.cancel()

            this.recreate()
        }

        myDialog.show()

    }

    fun image1OnClick(view: View) {


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
            Toast.makeText(this@Level1, "You did it.", Toast.LENGTH_SHORT).show()

            ShowDialog()
        }
    }


}

