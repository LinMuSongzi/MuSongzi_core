package com.android.playmusic.l.adapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.messcat.mclibrary.base.IChangeData
import com.musongzi.core.base.adapter.DataBindingViewHolder

abstract class ListAbstacyAdapter<ListItem>(var list: List<ListItem>) : RecyclerView.Adapter<DataBindingViewHolder>(),
    IChangeData<ListItem> {


    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    final override fun onBindViewHolder(holder: DataBindingViewHolder, position: Int) {
        convert(holder.getDataBinding(), list[position], position)
    }

    protected abstract fun convert(d: ViewDataBinding, item: ListItem, postion: Int);

    override fun getItemCount() = list.size

    override fun changeData(datas: List<ListItem>) {
        (list as? ArrayList)?.apply {
            clear()
            addAll(datas)
        }
    }
}