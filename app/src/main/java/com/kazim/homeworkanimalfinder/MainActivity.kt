package com.kazim.homeworkanimalfinder

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)

        if(sharedPreferences.contains("CURRENT_QUESTION")){
            val editor = sharedPreferences.edit()
            editor.putInt("CURRENT_QUESTION",1)
            editor.commit()
        }

        Toast.makeText(this, "current = ${sharedPreferences.getInt("CURRENT_QUESTION",0)}", Toast.LENGTH_SHORT).show()
    }

    fun buttonstart_onclick(view: View) {
        val intent = Intent(this, Level1::class.java)
        startActivity(intent)
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