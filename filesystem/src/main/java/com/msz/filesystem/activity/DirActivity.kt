package com.msz.filesystem.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.msz.filesystem.R
import com.msz.filesystem.fragment.DirListFragment

class DirActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().apply {
                add(R.id.id_content_layout, DirListFragment().apply {
                    arguments = intent.extras
                }, "FileRootFragment-main")
                commit()
            }
        }
    }



}