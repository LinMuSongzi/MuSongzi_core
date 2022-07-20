package com.musongzi.test

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import com.musongzi.comment.ExtensionMethod.startActivity
import com.musongzi.comment.ExtensionMethod.startActivityNormal
import com.musongzi.test.activity.BaseActivity
import com.musongzi.test.activity.MainIndexActivity
import com.musongzi.test.databinding.ActivityMainBinding
import com.musongzi.test.fragment.MainIndexFragment
import com.musongzi.test.fragment.TowFragment

class SplashActivity : BaseActivity() {

    lateinit var d: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        d = DataBindingUtil.setContentView(this, R.layout.activity_main)
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),0x99);
    }

    fun goTow(v: View) {
        MainIndexFragment::class.java.startActivityNormal(
            MainIndexActivity::class.java
        )
//        MainActivity::class.java.startActivity()
        //TowFragment::class.java.startActivityNormal("第二个页面")
    }

}
