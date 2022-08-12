package com.musongzi.music.impl

import android.os.Looper
import androidx.lifecycle.MutableLiveData
import com.musongzi.comment.util.SourceImpl
import com.musongzi.core.base.page.PageSupport
import com.musongzi.core.itf.IAttribute
import com.musongzi.core.itf.page.IPageEngine
import com.musongzi.core.itf.page.IRead2
import com.musongzi.core.itf.page.ISource
import com.musongzi.music.bean.Container
import com.musongzi.music.bean.MusicInfoContainer
import com.musongzi.music.itf.*
import com.musongzi.music.itf.IMusicArray.Companion.INDEX_NORMAL

/*** created by linhui * on 2022/7/28
 * 音乐队列[IMusicArray] 具体业务实现类
 * 管理队列播放下标[MusicArrayImpl.playIndexLiveData]
 * 管理数据加载[MusicArrayImpl.callBack] [MusicArrayImpl.musicPageEngine]
 *
 * 初始化时候获取到注入的数据加载模型和被观察者[RemoteDataPacket]
 *
 * 实现了[IRead2] 左右切换和更新歌曲
 * [IRead2.pre]上一首  ;  [IRead2.refresh]刷新   ;  [IRead2.next]下一首
 *
 * */
class MusicArrayImpl<I : IMediaPlayInfo, D>(name: String, dataPacket: RemoteDataPacket<I, D>) :
    Container<ISource<I>>(name), IMusicArray<I>, IRead2 {

    /**
     * 当前队列的播放器
     */
    private val controller: IPlayController by lazy {
        Factory.createProxyPlayMusicController(this@MusicArrayImpl)
    }

    /**
     * 音乐队列数据引擎
     */
    private val musicPageEngine: IPageEngine<I> by lazy {
        PageSupport(callBack)
    }

    /**
     * 当前播放的下标
     */
    private var playIndexLiveData: MutableLiveData<Int> =
        object : MutableLiveData<Int>(mixIndexState(0, INDEX_NORMAL)) {
            override fun setValue(value: Int) {
                if (Thread.currentThread() != Looper.getMainLooper().thread) {
                    postValue(value)
                } else {
                    super.setValue(value)
                }
            }
        }

    /**
     * 数据引擎的注入回调
     */
    private var callBack: PageSupport.CallBack<I, D>

    /**
     * 初始化了数据集合
     * 构建一个基本的音乐队列数据引擎
     * 新增一个音乐下标观察者，观察者用来观察下标来达到，音乐的播放
     */
    init {
        child = SourceImpl();
        callBack = MusicPageCallBack(dataPacket)
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

    override fun getHolderRead(): IRead2 {
        return this
    }

    override fun pre() {
        val index = getStateByMix(playIndexLiveData.value!!);
        if (index > 0) {
            playIndexLiveData.value = index - 1
        }
    }

    override fun refresh() {
        val index = getStateByMix(playIndexLiveData.value!!);
        if (index != 0) {
            playIndexLiveData.value = 0
        }
    }

    override fun next() {
        val index = getStateByMix(playIndexLiveData.value!!);
        if (index < realData().size - 1) {
            playIndexLiveData.value = index + 1
        }
    }

    companion object {
        fun mixIndexState(index: Int, state: Int): Int {
            if (state.and(IMusicArray.INDEX_MASK) != state) {
                return index
            }
            return index.or(state)
        }

        fun getStateByMix(indexMix: Int): Int {
            return indexMix.and(IMusicArray.INDEX_MASK.inv())
        }
    }

}