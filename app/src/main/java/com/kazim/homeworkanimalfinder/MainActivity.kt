package com.kazim.homeworkanimalfinder

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)

        val editor = sharedPreferences.edit()
        editor.putInt("CURRENT_QUESTION",1)
        editor.commit()

        Toast.makeText(this, "current = ${sharedPreferences.getInt("CURRENT_QUESTION",0)}", Toast.LENGTH_SHORT).show()
    }

    fun buttonstart_onclick(view: View) {
        val intent = Intent(this, Level1::class.java)
        startActivity(intent)
    }

}