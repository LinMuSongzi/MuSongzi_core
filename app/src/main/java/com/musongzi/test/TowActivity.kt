package com.musongzi.test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.musongzi.core.ExtensionMethod
import com.musongzi.test.databinding.ActivityMainBinding
import com.musongzi.test.databinding.ActivityTowBinding
import com.musongzi.test.engine.ArrayEngine
import com.musongzi.test.simple.TestMainFragment

class TowActivity : AppCompatActivity() {

    lateinit var d: ActivityTowBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        d = DataBindingUtil.setContentView(this, R.layout.activity_tow)
        supportFragmentManager.beginTransaction().replace(
            R.id.id_content_layout,
            createFragment2(), "haha"
        ).commitAllowingStateLoss()
    }

    private fun createFragment1(): Fragment {
        return TestMainFragment()
    }

    private fun createFragment2(): Fragment {
        return ExtensionMethod.analysisCollectionsEngine(ArrayEngine::class.java)
    }
}