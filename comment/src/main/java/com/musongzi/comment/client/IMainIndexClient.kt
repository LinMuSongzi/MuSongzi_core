package com.musongzi.comment.client

import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.musongzi.core.itf.IClient

/*** created by linhui * on 2022/7/21 */
interface IMainIndexClient :IClient{
     fun getRecycleView(): RecyclerView
     fun getViewpage2(): ViewPager2
}