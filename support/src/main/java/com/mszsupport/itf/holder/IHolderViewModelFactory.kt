package com.mszsupport.itf.holder

import androidx.lifecycle.ViewModelProvider

/*** created by linhui * on 2022/9/7 */
interface IHolderViewModelFactory {

    fun getHolderFactory():ViewModelProvider.Factory?

}