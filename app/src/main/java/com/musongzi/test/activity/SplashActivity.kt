package com.musongzi.test.activity

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.musongzi.comment.ExtensionMethod.startActivityNormal
import com.musongzi.comment.ExtensionMethod.startRecycleActivity
import com.musongzi.test.R
import com.musongzi.test.databinding.ActivityMainBinding
import com.musongzi.test.engine.ArrayEngine
import com.musongzi.test.fragment.SoulAppTestFragemnt
import com.musongzi.test.simple.BannerAndRetrofitMainFragment
import kotlinx.coroutines.*

class SplashActivity : BaseActivity() {

    lateinit var d: ActivityMainBinding


//    val mainScope = MainScope()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        d = DataBindingUtil.setContentView(this, R.layout.activity_main)
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            0x99
        );


        lifecycleScope.launch {
            Log.i("Continuation", "onCreate: start")

            val two = async {
                two()
            }.apply {
                start()
            }

            val three = async {
                three()
            }.apply {
                start()
            }
            Log.i("Continuation", "onCreate: ${two.await() + three.await()}")

//            Log.i(
//                "Continuation", "onCreate: sum = ${
//                    async {
//                        two()
//                    }.await() + async {
//                        three()
//                    }.await()
//                } "
//            )

//            val wait =  async {
//                withContext(Dispatchers.Default) {
//                    Thread.sleep(4000)
//                    Log.i("Continuation", "onCreate: Thread = ${Thread.currentThread().name}")
//                    "haha"
//                }
//            }
//
//            val waitResult = wait.start().apply {
//                wait.await()
//            }
//            Log.i("Continuation", "onCreate: 等待")
//            Log.i("Continuation", "onCreate: waitResult = $waitResult")

        }


    }

    suspend fun three(): Int {
        delay(2000)
        return 3;
    }

    suspend fun two(): Int {
        delay(3000)
        return 2
    }


    fun goTow(v: View) {
//        PictureSelector.create(this)
//            .openGallery(SelectMimeType.ofImage())
//            .setImageEngine(GlideEngine.createGlideEngine())
//            .forResult(object : OnResultCallbackListener<LocalMedia?> {
//                override fun onResult(result: ArrayList<LocalMedia?>?) {}
//                override fun onCancel() {}
//            })
//        MainIndexFragment::class.java.startActivityNormal(
//            "主页",
//            MainIndexActivity::class.java,
//            Color.WHITE,
//            null, //TestMainIndexBusiness::class.java.name
//        )
//        MainActivity::class.java.startActivity()
//        TowFragment::class.java.startActivityNormal("第二个页面")

        SoulAppTestFragemnt::class.java.startActivityNormal("滑动测试")

//        BannerAndRetrofitMainFragment::class.java.startActivityNormal("Banner请求")

//        RecyleViewCheckFragment::class.java.startActivityNormal("测试recycle")

//        TowActivity::class.java.startActivity()

//        Camra2Fragment::class.java.startActivityNormal("TestMainFragment")
//        CollectionsViewFragment.

//        ArrayEngine::class.java.convertFragment().asInterfaceByEngine {
//
//        }

//        ArrayEngine::class.java.startRecycleActivity("选择")

    }

}
