package com.musongzi.test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.musongzi.core.ExtensionMethod
import com.musongzi.core.ExtensionMethod.analysisCollectionsEngine
import com.musongzi.test.activity.BaseActivity
import com.musongzi.test.databinding.ActivityMainBinding
import com.musongzi.test.databinding.ActivityTowBinding
import com.musongzi.test.engine.ArrayEngine
import com.musongzi.test.simple.TestMainFragment

class TowActivity : BaseActivity() {

    lateinit var d: ActivityTowBinding

    var f = createFragment1()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        d = DataBindingUtil.setContentView(this, R.layout.activity_tow)
        go()
        window.decorView.postDelayed({
            replace()
        }, 3000)

        window.decorView.postDelayed({
            go()
        }, 10000)
    }

    private fun replace() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.id_content_layout, createFragment2()).commit()
    }

    private fun go() {
        supportFragmentManager.beginTransaction().replace(
            R.id.id_content_layout,
            f, "haha"
        ).commitAllowingStateLoss()
    }

    private fun createFragment1(): Fragment {
        return TestMainFragment()
    }

    private fun createFragment2(): Fragment {
        return analysisCollectionsEngine(ArrayEngine::class.java)
    }
}