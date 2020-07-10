package com.example.covidct

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private val SPLASH_TIME : Long = 5000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val topAnim = AnimationUtils.loadAnimation(this,R.anim.top_res)
        val bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_res)
        var mainText = findViewById<TextView>(R.id.mainText)
        var subText = findViewById<TextView>(R.id.subText)
        mainText.setAnimation(topAnim)
        subText.setAnimation(bottomAnim)
        Handler().postDelayed({
            startActivity(Intent(this, dashBoard::class.java))
        },SPLASH_TIME)
    }
}
