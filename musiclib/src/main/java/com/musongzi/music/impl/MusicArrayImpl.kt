package com.musongzi.music.impl

import android.os.Looper
import androidx.lifecycle.MutableLiveData
import com.musongzi.comment.util.SourceImpl
import com.musongzi.core.base.page.PageSupport
import com.musongzi.core.itf.IAttribute
import com.musongzi.core.itf.page.IPageEngine
import com.musongzi.core.itf.page.ISource
import com.musongzi.music.bean.Container
import com.musongzi.music.bean.MusicInfoContainer
import com.musongzi.music.itf.*
import com.musongzi.music.itf.IMusicArray.Companion.INDEX_NORMAL

/*** created by linhui * on 2022/7/28
 * 音乐队列
 *  管理队列播放
 *
 * */
class MusicArrayImpl<I : IMediaPlayInfo, D>(name: String, dataProxy: MusicDataProxy<I, D>) :
    Container<ISource<I>>(name), IMusicArray<I> {

    private val controller: IPlayController by lazy {
        Factory.createProxyPlayMusicController(this@MusicArrayImpl)
    }
    private val musicPageEngine: IPageEngine<I> by lazy {
        PageSupport(callBack)
    }

    private var playIndexLiveData: MutableLiveData<Int> = object : MutableLiveData<Int>(0) {
        override fun setValue(value: Int) {
            if (Thread.currentThread() != Looper.getMainLooper().thread) {
                postValue(value)
            } else {
                super.setValue(value)
            }
        }
    }
    private var callBack: PageSupport.CallBack<I, D>
    init {
        child = SourceImpl();
        callBack = MusicPageCallBack(dataProxy)
        playIndexLiveData.observeForever {
            if (it.and(INDEX_NORMAL) > 0) {
                return@observeForever
            }
            getPlayController().playMusicByInfo(realData()[it])
        }
    }



    override fun thisPlayIndex(): Int {
        return playIndexLiveData.value!!
    }

    override fun changeThisPlayIndex(index: Int) {
        playIndexLiveData.value = index
    }

    override fun changeThisPlayIndexAndAdd(stringUrl: String) {
        val info = PlayQueueManagerImpl.getInstance().partnerInstance.createMusicInfo(stringUrl)
        (realData() as ArrayList).add(0, MusicInfoContainer(info) as I)
    }




    override fun getPlayController(): IPlayController {
        return controller;
    }


    override fun contains(att: IAttribute, find: ((IAttribute) -> Boolean)?): Boolean {
        return if (find == null) {
            realData().contains(att)
        } else {
            var f = false;
            for (v in realData()) {
                f = find.invoke(v)
                if (f) {
                    break
                }
            }
            f
        }
    }

    override fun getHolderPageEngine(): IPageEngine<I> {
        return musicPageEngine
    }

    override fun realData(): List<I> = child!!.realData()
}