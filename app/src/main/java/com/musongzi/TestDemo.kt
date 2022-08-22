package com.musongzi

import android.os.Environment
import android.util.Log
import com.musongzi.core.util.ActivityThreadHelp
import java.io.File
import java.io.RandomAccessFile

/*** created by linhui * on 2022/8/22 */
object TestDemo {


    fun fileRandomAccessFile(){
        val f = File(ActivityThreadHelp.getCurrentApplication().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "haha")
        if (!f.exists()) {
            f.mkdirs()
        }

        val file = File(f, "test_2.txt")
        if (!file.exists()) {
            file.createNewFile()
        }
        val r = RandomAccessFile(file, "rws")
        Log.i(TAG, "filePointer:start ${file.length()}")
        if (file.length() != 0L) {
            r.seek(file.length())
            r.write("\n".toByteArray())
        }
        r.write("你好吗?".toByteArray())
        r.close()
        Log.i(TAG, "filePointer:end   ${file.length()}")
    }

}