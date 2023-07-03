package com.msz.filesystem

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.msz.filesystem.fragment.FileRootFragment


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(savedInstanceState == null){
            supportFragmentManager.beginTransaction().apply {
                add(R.id.id_content_layout,FileRootFragment(),"FileRootFragment-main")
                commit()
            }
        }
    }

}