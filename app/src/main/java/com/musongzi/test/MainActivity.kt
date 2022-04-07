package com.musongzi.test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.musongzi.test.databinding.ActivityMainBinding
import com.musongzi.test.simple.TestMainFragment

class MainActivity : AppCompatActivity() {

    lateinit var d: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        d = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    fun goTow(v: View) {
        startActivity(Intent(this, TowActivity::class.java))
    }

}
