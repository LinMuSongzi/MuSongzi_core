package com.musongzi.core.view

import androidx.recyclerview.widget.RecyclerView
import com.musongzi.core.itf.IAgent

/*** created by linhui * on 2022/7/20  */
interface IMainBottomView :IAgent{

    fun getRecycleView():RecyclerView

}