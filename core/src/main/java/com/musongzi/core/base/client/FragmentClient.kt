package com.musongzi.core.base.client

import com.musongzi.core.itf.IClient
import com.musongzi.core.itf.holder.IHolderFragmentManager

interface FragmentClient :IHolderFragmentManager,IClient{

    fun layoutId():Int

}