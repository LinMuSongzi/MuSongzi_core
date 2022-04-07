package com.musongzi.core.itf

import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.musongzi.core.itf.page.IPageEngine
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import io.reactivex.rxjava3.annotations.Nullable

interface IDictionary<Item> : IPageEngine<Item>, IBusiness {
    fun handlerArguments(bundle: @Nullable Bundle?)
    fun handlerView(r: @Nullable RecyclerView?, s: @Nullable SmartRefreshLayout?)
}