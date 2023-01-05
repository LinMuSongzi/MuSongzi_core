package com.mszsupport.itf.holder

import com.mszsupport.itf.IBusiness

interface IHolderBusiness<B : IBusiness> {

    fun getHolderBusiness():B?


}