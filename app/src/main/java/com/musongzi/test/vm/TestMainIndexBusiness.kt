package com.musongzi.test.vm

import android.content.ContentValues
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.FileUtils
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.musongzi.FutureDemo
import com.musongzi.FutureDemo.acceptEither
import com.musongzi.comment.ExtensionMethod.saveStateChange
import com.musongzi.comment.business.MainIndexBusiness
import com.musongzi.comment.util.ApkUtil
import com.musongzi.core.ExtensionCoreMethod.sub
import com.musongzi.core.URLUtil
import com.musongzi.core.base.manager.ActivityLifeManager
import com.musongzi.core.base.vm.CoreViewModel
import com.musongzi.mExecutor
import com.musongzi.test.Api
import java.io.File
import java.io.FileOutputStream
import java.io.InputStreamReader
import java.io.RandomAccessFile
import java.nio.charset.Charset
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutorService
import java.util.concurrent.ForkJoinPool

@RequiresApi(Build.VERSION_CODES.N)
/*** created by linhui * on 2022/7/25 */
class TestMainIndexBusiness : MainIndexBusiness() {


    override fun afterHandlerBusiness() {
        mExecutor.execute {
            ActivityLifeManager.getInstance().getTopActivity()?.also {

//                if (Build.VERSION.SDK_INT > 28) {
//                    val c = it.contentResolver.query(
//                        MediaStore.Downloads.EXTERNAL_CONTENT_URI,
//                        arrayOf(
//                            MediaStore.Files.FileColumns.TITLE,
//                            MediaStore.Files.FileColumns.DISPLAY_NAME
//                        ),
//                        "${MediaStore.Files.FileColumns.TITLE} = ?", arrayOf("linhui_text"), null
//                    )
//                    if (c?.count == 0) {
//
//                        val contentValues = ContentValues()
//                        contentValues.put(MediaStore.Files.FileColumns.RELATIVE_PATH, "linhui.txt")
//                        contentValues.put(MediaStore.Files.FileColumns.DISPLAY_NAME, "linhui.text")
//                        contentValues.put(MediaStore.Files.FileColumns.TITLE, "linhui_text")
//                        val uri = it.contentResolver.insert(
//                            MediaStore.Files.getContentUri("external"),
//                            contentValues
//                        )
//                        uri?.apply {
//                            val outputStream = it.contentResolver.openOutputStream(uri)
//                            outputStream?.write("你好吗".toByteArray(Charset.defaultCharset()))
//                            outputStream?.close()
//                        }
//
//                    } else {
//                        c?.let { cn ->
//                            if (cn.moveToNext()) {
//                                val data =
//                                    cn.getString(cn.getColumnIndexOrThrow(MediaStore.Files.FileColumns.RELATIVE_PATH))
//                                it.contentResolver.openInputStream(Uri.parse(data))?.apply {
////                                    var read = Inpu(this)
//                                    val values = ByteArray(1024)
//                                    var length = 0
//                                    do {
//                                        length = read(values)
//                                        Log.i(
//                                            TAG,
//                                            "afterHandlerBusiness: values = " + String(values)
//                                        )
//                                    } while (length != 1)
//                                }
//                            }
//                        }
//
//                    }
//                }
//                Log.i(TAG, "afterHandlerBusiness: ${f.absolutePath}")
            }
        }
    }


    override fun buildFragments(): List<Fragment> {

        iAgent.holderApiInstance<Api>()?.getApi()?.getArrayEngine(0)?.sub {
//
            Log.i(TAG, "buildFragments: ${it[0].title} " + javaClass.name)
//
        }


        val save = (iAgent as CoreViewModel<*>).localSavedStateHandle()
        save.getLiveData<String>("haha").observe(iAgent.getThisLifecycle()!!) {
            Log.i(TAG, "buildFragments: test local value = $it")
        }
        save["haha"] = "1"


        "haha".saveStateChange(iAgent, 998777)


//        ArrayList<String>().toArray()

//        val clazz :KClass<ArrayEngine> = ArrayEngine::class
//        Log.i(TAG, "buildFragments: "+clazz.supertypes)
//        Log.i(TAG, "buildFragments: "+(clazz.annotations[0] as com.musongzi.core.annotation.CollecttionsEngine).emptyString)
//        for(vc in clazz.supertypes){
//
//            Log.i(TAG, "buildFragments: "+vc.arguments.let {
//                if(it.isNotEmpty()){
//                    (it[0].type?.classifier as KClass<*>)
//                }else{
//                    it
//                }
//            })
//
//        }


        return ArrayList()
    }

}