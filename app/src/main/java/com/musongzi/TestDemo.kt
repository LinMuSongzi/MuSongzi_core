package com.musongzi

import android.os.Environment
import android.util.Log
import com.musongzi.core.util.ActivityThreadHelp
import java.io.File
import java.io.RandomAccessFile

/*** created by linhui * on 2022/8/22 */
object TestDemo {


    /**
     * 访问自己外部是有文件夹
     * 不需要权限
     */
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

    fun fileMediaTest(){

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