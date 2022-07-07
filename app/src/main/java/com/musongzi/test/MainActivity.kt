package com.musongzi.test

import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import com.musongzi.core.base.activity.MszFragmentActivity
import com.musongzi.test.databinding.ActivityMainBinding
class MainActivity : MszFragmentActivity() {

    lateinit var d: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        d = DataBindingUtil.setContentView(this, R.layout.activity_main)
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),0x99);
    }

    fun goTow(v: View) {
        startActivity(Intent(this, TowActivity::class.java))

    }

}
