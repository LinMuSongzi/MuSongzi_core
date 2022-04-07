package com.musongzi.core.itf.holder

import com.musongzi.core.itf.IBusiness

interface IHolderBusiness<B : IBusiness> {

    fun getHolderBusiness():B?


}