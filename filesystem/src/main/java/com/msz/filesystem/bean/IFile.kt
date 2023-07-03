package com.msz.filesystem.bean

import JumActivity
import android.app.Application
import android.content.Context
import android.content.Intent
import com.msz.filesystem.activity.DirActivity
import com.musongzi.core.util.ActivityThreadHelp

interface IFile {

    var fileType: Int
    val path: String


    companion object {

        fun IFile.isFile(): Boolean {
            return fileType == FILE_TYPE
        }

        fun IFile.startDir(context: Context? = null) {
            JumActivity.starDirActivity(context = context, file = this)

        }

        const val ROOT = "root_msz"
        const val ROOT_TIME = -1L
        const val DIR_TYPE = 1
        const val FILE_TYPE = 2

    }

}