package com.musongzi.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.musongzi.test.databinding.ActivityMainBinding
import com.musongzi.test.simple.TestMainFragment

class MainActivity : AppCompatActivity() {

    lateinit var d: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        d = DataBindingUtil.setContentView(this, R.layout.activity_main)
        supportFragmentManager.beginTransaction().replace(R.id.id_content_layout,
            TestMainFragment(),"haha").commitAllowingStateLoss()
    }
}
