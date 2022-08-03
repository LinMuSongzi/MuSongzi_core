package com.musongzi.music.impl

import com.musongzi.comment.util.SourceImpl
import com.musongzi.core.itf.IAttribute
import com.musongzi.core.itf.page.IPageEngine
import com.musongzi.core.itf.page.ISource
import com.musongzi.music.bean.MusicPlayInfoImpl
import com.musongzi.music.itf.*
import io.reactivex.rxjava3.core.Observable

/*** created by linhui * on 2022/7/28 */
abstract class MusicArrayImpl(private val dataProxy: MusicDataProxy<Any>) : IMusicArray {
    private var id: String = "" + hashCode()
    private val sourceImpl: ISource<MusicPlayInfoImpl> = SourceImpl()
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

    override fun getPageEngine(): IPageEngine<MusicPlayInfoImpl> {
        TODO("Not yet implemented")
    }

    override fun getAttributeId(): String = id

    override fun transformDataToList(entity: Any): List<MusicPlayInfoImpl> {
        return dataProxy.transformDataToList(entity)
    }

    override fun getRemoteData(page: Int): Observable<Any>? {
        return dataProxy.getRemoteData(page)
    }

    override fun pageSize(): Int = IPageEngine.PAGE_SIZE

    override fun thisStartPage(): Int = 0;

    override fun handlerData(t: MutableList<MusicPlayInfoImpl>?, action: Int) {
        TODO("Not yet implemented")
    }

    override fun realData(): List<MusicPlayInfoImpl> = sourceImpl.realData()
}