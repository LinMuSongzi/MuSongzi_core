package com.musongzi.core.base.vm

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import com.musongzi.core.itf.*
import com.musongzi.core.itf.client.IContextClient
import com.musongzi.core.itf.holder.*
import com.musongzi.core.util.InjectionHelp
import java.lang.ref.WeakReference

@Deprecated("不推荐使用")
abstract class ClientViewModel<C : IClient, B : IBusiness> : DataDriveViewModel<B>,
    IHolderClientViewModel<C, B>,IHolderContext {

    override fun getHolderContext(): Context? {
        return client?.get() as? Context
    }

    constructor() : super()
    constructor(saved: SavedStateHandle) : super(saved)

    private var client: WeakReference<C?>? = null
    val attachClientFlag = 0;

    @Deprecated("置换V层Client，不建议使用", ReplaceWith("this.client = client"))
    fun setHolderClient(client: C) {
        this.client = WeakReference(client)
    }

    override fun attachNow(t: IContextClient?) {
        super.attachNow(t)
        this::class.java.apply {
            InjectionHelp.attachClient(this@ClientViewModel, this, t, ClientViewModel::class.java.name, 0);
        }
    }

    override fun indexBusinessActualTypeArgument() = 1

    override fun getHolderClient(): C? {
        return client?.get()
            ?: holderActivity?.get() as? C//com.heart.core.util.InjectionHelp.checkClient(holderActivity?.get() as? C, javaClass, indexClientActualTypeArgument())
    }

    protected open fun indexClientActualTypeArgument(): Int = 0;


}