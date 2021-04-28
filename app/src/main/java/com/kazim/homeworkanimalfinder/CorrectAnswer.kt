package com.kazim.homeworkanimalfinder

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class CorrectAnswer : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_correct_answer)
    }

    fun nextQuestionButtonOnClick(view: View) {
        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("CURRENT_QUESTION",sharedPreferences.getInt("CURRENT_QUESTION",0)+1)
        editor.commit()

        val intent = Intent(this,Level1::class.java)
        startActivity(intent)

    }


}