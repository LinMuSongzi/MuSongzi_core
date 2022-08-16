package com.musongzi.comment.client

import android.app.Activity
import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.musongzi.core.itf.IClient
import com.musongzi.core.itf.holder.IHolderContext

/*** created by linhui * on 2022/7/21 */
interface IMainIndexClient :IClient, IHolderContext {
     fun getRecycleView(): RecyclerView
     fun getViewpage2(): ViewPager2
     override fun getHolderContext() : Activity
}