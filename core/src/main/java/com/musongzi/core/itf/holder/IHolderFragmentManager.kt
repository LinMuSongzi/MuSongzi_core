package com.musongzi.core.itf.holder

import androidx.fragment.app.FragmentManager

interface IHolderFragmentManager {

    fun getHolderFragmentManager(): FragmentManager

    fun getHolderParentFragmentManager(): FragmentManager?

}