package com.musongzi.comment.client

import com.musongzi.comment.IMainIndexClient
import com.musongzi.core.itf.IAgent
import com.musongzi.core.itf.IClient
import com.musongzi.core.itf.IHolderSavedStateHandle
import com.musongzi.core.itf.ILifeSaveStateHandle
import com.musongzi.core.itf.holder.IHolderClient

/*** created by linhui * on 2022/7/20 */
interface IMainIndexViewModel: ILifeSaveStateHandle,IAgent {

    override fun getHolderClient():IMainIndexClient?

}