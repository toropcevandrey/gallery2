package com.example.gallery2.features.mainactivity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.gallery2.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Gallery2)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun signOut() {
        val i = applicationContext.packageManager
            .getLaunchIntentForPackage(applicationContext.packageName)
        i!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(i)
    }
}