package com.musongzi.test.simple

import android.os.Environment
import android.util.Log
import com.musongzi.core.base.fragment.ModelFragment
import com.musongzi.core.base.manager.ActivityLifeManager.Companion.event
import com.musongzi.core.base.manager.ActivityLifeManager.Companion.registerEvent
import com.musongzi.core.databinding.FragmentTestMainBinding
import com.musongzi.test.Enter
import com.musongzi.test.ITestClient
import com.musongzi.test.bean.DiscoverBannerBean
import com.musongzi.test.event.ILoginEvent
import com.musongzi.test.vm.TestViewModel
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.io.*
import java.lang.Math.abs
import java.nio.charset.Charset
import java.text.SimpleDateFormat

class TestMainFragment : ModelFragment<TestViewModel, FragmentTestMainBinding>(), ITestClient,
    ILoginEvent {

    companion object {
        const val MAX_OUNT = 100_000
        const val FOTMAT_DATA = "MM:dd HH:mm:ss:SSS"
    }

    override fun initData() {
        dataBinding.idMainContentTv.setOnClickListener {

            if (!dataBinding.idEdittext.text.isNullOrEmpty()) {
//                var byte = ByteArrayInputStream(dataBinding.idEdittext.text.toStr.toByteArray()ing());

            }

        }

    }

    fun testClick() {
//        Thread {
        val sl = System.currentTimeMillis()
        activity?.runOnUiThread {
            Log.i(
                TAG,
                "initEvent: start ${SimpleDateFormat(FOTMAT_DATA).format(System.currentTimeMillis())}"
            )
        }
//            for (v in 1..MAX_OUNT) {
        ILoginEvent::class.java.event()?.onLogin()
//            IMusicEvent::class.java.event()?.play()
//            EventBus.getDefault().post(DiscoverBannerBean())
//            }
        val el = System.currentTimeMillis()
        activity?.runOnUiThread {
            Log.i(
                TAG,
                "initEvent:   end ${SimpleDateFormat(FOTMAT_DATA).format(System.currentTimeMillis())}"
            )
            Log.i(TAG, "initEvent: time ${abs(sl - el)}")
        }

//        }.start()
    }


    override fun initEvent() {
        dataBinding.idMainContentTv.setOnClickListener {
            testClick()
        }
    }

    var count = 0

    override fun onLogin() {
//        count++
//        if (count == MAX_OUNT) {
        Log.i(TAG, "initEvent onLogin: count = $count")
//        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessage(d: DiscoverBannerBean) {
        count++
        if (count == MAX_OUNT) {
            Log.i(TAG, "initEvent onMessage: $count")
        }
    }

    override fun showDialog(msg: String?) {
//        super.showDialog(msg)
//        Log.i(TAG, "EventManger showDialog: msg = $msg ")
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    override fun initView() {
        EventBus.getDefault().register(this)
        registerEvent(ILoginEvent::class.java) {
            this
        }

        Log.i(TAG, "initView: ${getMainViewModel()}")
        getMainViewModel()?.business?.checkBanner()
    }

    override fun showText(msg: String) {
        var m = "上述例子中的 s 已经乱码了，当你操作这个 s 获取字节也是乱码的。";
        Thread {
            val file = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                    .toString() + File.separator + "加密.txt"
            )
            if (file.exists()) {
                file.delete()
            }
            val outputStream = FileOutputStream(file, true)
            Log.i(TAG, "showText: 开始 ->$m")
            Enter.jiami(ByteArrayInputStream(m.toByteArray()), outputStream)

            Log.i(TAG, "Enter_now *********\n**************")
            val bOut = FileOutputStream(File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                        .toString() + File.separator + "解密.txt"
                ), true)
//            val bOut = ByteArrayOutputStream();
            Enter.jiami(FileInputStream(file), bOut)
            bOut.close()
//            Log.i(TAG, "Enter_now:输出 " + bOut.toString(Charsets.UTF_8.name()))

        }.start()
    }


    override fun onLogout() {

    }


}