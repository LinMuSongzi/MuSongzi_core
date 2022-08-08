package com.musongzi.music.bean

import android.net.Uri
import com.musongzi.music.itf.IMediaPlayInfo

/*** created by linhui * on 2022/8/9 */
class MusicInfoContainer(child:IMediaPlayInfo) : Container<IMediaPlayInfo>(),IMediaPlayInfo{

    init {
        this.child = child
    }

    override fun setUri(uri: Uri) {
        child?.setUri(uri)
    }

    override fun setRes(res: Int) {
        child?.setRes(res = res)
    }

    override fun setUriString(dataString: String) {
        child?.setUriString(dataString = dataString)
    }

    override fun getHolderRes(): Int? {
       return child?.getHolderRes()
    }

    override fun holderFlag(): Int {
      return  child?.holderFlag()!!
    }

    override fun getHolderUri(): Uri? {
       return child?.getHolderUri()
    }

    override fun getHolderSting(): String? {
        return child?.getHolderSting()
    }
}