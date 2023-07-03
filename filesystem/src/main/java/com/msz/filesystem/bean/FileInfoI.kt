package com.msz.filesystem.bean

import android.os.Parcelable
import com.msz.filesystem.R
import com.msz.filesystem.bean.IFile.Companion.DIR_TYPE
import com.musongzi.core.base.bean.BaseChooseBean

import kotlinx.android.parcel.Parcelize

@Parcelize
class FileInfoI(
    val name: String,
    override val path: String,
    override var fileType: Int,
    val cover: String? = null,
    val createTime: Long? = null,
    val fileFormat: String? = null,
    val allName: String? = null,
    override var token: String? = null
) : ITokenInfo, IFile, Parcelable, BaseChooseBean() {

    companion object {

    }

    override var id_: String
        get() {
            return path
        }
        set(value) {

        }


    val src: Int
        get() {
            return when (fileType) {
                DIR_TYPE -> {
                    R.mipmap.ic_folder
                }
                else -> {
                    when (if (path.endsWith("\\.")) (path.split("\\.").run {
                        get(size - 1)
                    }) else "") {
                        "text" -> {
                            R.mipmap.ic_txt
                        }

                        "gif" -> {
                            R.mipmap.ic_gif
                        }

                        "jpeg", "png", "webp", "jpg" -> {
                            R.mipmap.ic_image
                        }

                        "mp4" -> {
                            R.mipmap.ic_video
                        }

                        "mp3" -> {
                            R.mipmap.ic_audio
                        }

                        "xml" -> {
                            R.mipmap.ic_xml
                        }

                        "pdf" -> {
                            R.mipmap.ic_pdf
                        }

                        "ppt" -> {
                            R.mipmap.ic_ppt
                        }

                        "exe" -> {
                            R.mipmap.ic_exe
                        }

                        "zip", "wap" -> {
                            R.mipmap.ic_yasuo
                        }

                        "html", "HTML" -> {
                            R.mipmap.ic_html
                        }

                        else -> {
                            R.mipmap.ic_normal_file
                        }
                    }
                }
            }
        }

}
