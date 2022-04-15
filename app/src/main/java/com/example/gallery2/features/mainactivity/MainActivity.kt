package com.example.gallery2.features.mainactivity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.gallery2.R

/***
 * По всему проекту желательно уйти от lateinit и использовать by lazy +++
 * Можно базовые вещи (к примеру, инициализация биндинга) вынести в BaseFragment +++
 * Можно смело выпиливать tokenTime +++
 * */

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Gallery2)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun signOut() {
        packageManager.getLaunchIntentForPackage(packageName)?.apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        }?.let {
            startActivity(it)
        }
    }
}