package com.musongzi.core.itf.holder

import androidx.fragment.app.Fragment
import com.musongzi.core.itf.client.IContextClient

interface IHolderFragment : IContextClient {

    fun getHolderFragment():Fragment

}