package com.musongzi.test.activity

import android.Manifest
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.config.SelectMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnResultCallbackListener
import com.musongzi.GlideEngine
import com.musongzi.comment.ExtensionMethod.asInterfaceByEngine
import com.musongzi.comment.ExtensionMethod.convertFragemnt
import com.musongzi.comment.ExtensionMethod.startActivityNormal
import com.musongzi.comment.ExtensionMethod.startRecyeleActivity
import com.musongzi.test.R
import com.musongzi.test.databinding.ActivityMainBinding
import com.musongzi.test.engine.ArrayEngine
import com.musongzi.test.fragment.Camra2Fragment
import com.musongzi.test.fragment.MainIndexFragment
import com.musongzi.test.fragment.TowFragment
import com.musongzi.test.simple.TestMainFragment
import com.musongzi.test.vm.TestMainIndexBusiness


class SplashActivity : BaseActivity() {

    lateinit var d: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        d = DataBindingUtil.setContentView(this, R.layout.activity_main)
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            0x99
        );
    }

    fun goTow(v: View) {
//        PictureSelector.create(this)
//            .openGallery(SelectMimeType.ofImage())
//            .setImageEngine(GlideEngine.createGlideEngine())
//            .forResult(object : OnResultCallbackListener<LocalMedia?> {
//                override fun onResult(result: ArrayList<LocalMedia?>?) {}
//                override fun onCancel() {}
//            })
        MainIndexFragment::class.java.startActivityNormal(
            "主页",
            MainIndexActivity::class.java,
            Color.WHITE,
            null, //TestMainIndexBusiness::class.java.name
        )
//        MainActivity::class.java.startActivity()
//        TowFragment::class.java.startActivityNormal("第二个页面")
//        TestMainFragment::class.java.startActivityNormal("TestMainFragment")

//        Camra2Fragment::class.java.startActivityNormal("TestMainFragment")
//        CollectionsViewFragment.

//        ArrayEngine::class.java.convertFragemnt().asInterfaceByEngine {
//
//        }

//        ArrayEngine::class.java.startRecyeleActivity("测试列表")

    }

}
