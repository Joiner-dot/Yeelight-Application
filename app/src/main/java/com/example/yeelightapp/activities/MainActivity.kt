package com.example.yeelightapp.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.yeelightapp.R


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar!!.hide();
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}