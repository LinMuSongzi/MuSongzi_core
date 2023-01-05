package com.mszsupport.itf

import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.mszsupport.itf.page.IPageEngine
import com.mszsupport.itf.view.ISmartRefreshLayout
import io.reactivex.rxjava3.annotations.Nullable

interface IDictionary<Item> : IPageEngine<Item>, IBusiness {
    fun handlerArguments(bundle: @Nullable Bundle?)
    fun handlerView(r: @Nullable RecyclerView?, s: @Nullable ISmartRefreshLayout?)
}