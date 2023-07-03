package com.msz.filesystem.bean

import JumActivity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.util.Log
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.entity.LocalMedia
import com.msz.filesystem.R
import com.msz.filesystem.instance.RetrofitIntance
import com.musongzi.core.base.manager.ActivityLifeManager
import com.musongzi.core.itf.page.ISource


interface IFile {

    var fileType: Int
    val path: String
    var name: String

    fun getLastIndexStr(): String {
        return name.substring(name.lastIndexOf(".").let {
            if (it == -1) {
                0
            } else if (it + 1 >= name.length) {
                it
            } else {
                it + 1
            }
        })
    }

    fun isPicture(): Boolean {
        return isFile() && getLastIndexStr().let {
            it == "jpeg" || it == "png" || it == "webp" || it == "jpg"
        }
    }

    fun isVideo(): Boolean {
        return isFile() && getLastIndexStr().let {
            it == "mp4" || it == "rmvb" || it == "wmv" || it == "flv" || it == "ASF" || it == "avi"
        }
    }

    companion object {

        fun IFile.isFile(): Boolean {
            return fileType == FILE_TYPE
        }

        fun IFile.startDir(context: Context? = null) {
            JumActivity.starDirActivity(context = context, file = this)
        }

        fun IFile.startFileAction(list: List<IFile>? = null) {
            ActivityLifeManager.getInstance().getTopActivity()?.apply {
                if (isPicture()) {
                    if (!list.isNullOrEmpty()) {
                        PictureSelector.create(this).openPreview().startActivityPreview(0, false, ArrayList<LocalMedia>().apply {
                            add(LocalMedia.create().also {
                                it.path = asPathUrl()
                                it.fileName = this@startFileAction.name
                            })
                        })
                    } else {
                        PictureSelector.create(this).openPreview()
                            .startActivityPreview(list!!.indexOf(this@startFileAction), false, ArrayList<LocalMedia>().apply {
                                for (info in list) {
                                    if (info.isPicture()) {
                                        add(LocalMedia.create().also {
                                            it.path = asPathUrl()
                                            it.fileName = this@startFileAction.name
                                        })
                                    }
                                }
                            })
                    }
                } else if (isVideo()) {
                    val uri = Uri.parse(asPathUrl())
                    //调用系统自带的播放器
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.setDataAndType(uri, "video/mp4")
                    startActivity(intent)
                }
            }

        }

        fun IFile.asPathUrl(): String {
            return RetrofitIntance.URL2 + "disk/file2?acceseKey=${ROOT}&path=${path}"
        }

        fun IFile.startDirOrOtherBySource(tip: Boolean, list: ISource<IFile>? = null) {
            startDirOrOther(tip, list?.realData())
        }

        fun IFile.startDirOrOther(tip: Boolean, list: List<IFile>? = null) {
            if (!isFile()) {
                startDir()
            } else if (tip && (isPicture() || isVideo())) {
                ActivityLifeManager.getInstance().getTopActivity()?.apply {
                    AlertDialog.Builder(this).setIcon(R.mipmap.ic_logo_file).setTitle("是否打开文件").setMessage(this@startDirOrOther.name)
                        .setNeutralButton("确定") { d, _ ->
                            d.dismiss()
                            startFileAction()
                        }.setNegativeButton("取消") { d, _ ->
                            d.dismiss()
                        }.create().show()
                }
            } else {
                startFileAction(list)
            }
        }


        const val ROOT = "root_msz"
        const val ROOT_TIME = -1L
        const val DIR_TYPE = 1
        const val FILE_TYPE = 2

    }

}