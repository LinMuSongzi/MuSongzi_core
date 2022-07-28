package com.musongzi.music.impl

import com.musongzi.comment.util.SourceImpl
import com.musongzi.core.itf.IAttribute
import com.musongzi.core.itf.page.IPageEngine
import com.musongzi.core.itf.page.ISource
import com.musongzi.music.Factory
import com.musongzi.music.bean.MusicPlayInfo
import com.musongzi.music.itf.AttributeArray
import com.musongzi.music.itf.IHolderPlayController
import com.musongzi.music.itf.IPlayController
import com.musongzi.music.itf.IPlayQueueManager
import io.reactivex.rxjava3.core.Observable

/*** created by linhui * on 2022/7/28 */
abstract class MusicArrayImpl(
    val dataProxy: MusicDataProxy<Any>,
    private val manager: IPlayQueueManager
) : AttributeArray<MusicPlayInfo, Any>, IHolderPlayController {
    private var id: String = "" + hashCode()
    private val sourceImpl: ISource<MusicPlayInfo> = SourceImpl()
    private var playIndex = 0
    private val controller: IPlayController by lazy {
        Factory.createPlayMusicController(this)
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

    override fun getPageEngine(): IPageEngine<MusicPlayInfo> {
        TODO("Not yet implemented")
    }

    override fun getAttributeId(): String = id

    override fun transformDataToList(entity: Any): List<MusicPlayInfo> {
        return dataProxy.transformDataToList(entity)
    }

    override fun getRemoteData(page: Int): Observable<Any>? {
        return dataProxy.getRemoteData(page)
    }

    override fun pageSize(): Int = IPageEngine.PAGE_SIZE

    override fun thisStartPage(): Int = 0;

    override fun handlerData(t: MutableList<MusicPlayInfo>?, action: Int) {
        TODO("Not yet implemented")
    }

    override fun realData(): List<MusicPlayInfo> = sourceImpl.realData()
}