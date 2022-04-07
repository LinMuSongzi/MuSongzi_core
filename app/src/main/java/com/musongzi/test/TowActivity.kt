package com.musongzi.test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.musongzi.test.databinding.ActivityMainBinding
import com.musongzi.test.databinding.ActivityTowBinding
import com.musongzi.test.simple.TestMainFragment

class TowActivity : AppCompatActivity() {

    lateinit var d: ActivityTowBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        d = DataBindingUtil.setContentView(this, R.layout.activity_tow)
        supportFragmentManager.beginTransaction().replace(
            R.id.id_content_layout,
            TestMainFragment(), "haha"
        ).commitAllowingStateLoss()
    }
}