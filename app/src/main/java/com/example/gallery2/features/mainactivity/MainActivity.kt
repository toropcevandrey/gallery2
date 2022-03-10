package com.example.gallery2.features.mainactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gallery2.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Gallery2)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}