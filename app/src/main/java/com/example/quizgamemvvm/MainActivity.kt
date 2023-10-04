package com.example.quizgamemvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.quizgamemvvm.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var mainActivityBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainActivityBinding.root)
        Log.d("MainActivity", "MainActivity")
    }
}