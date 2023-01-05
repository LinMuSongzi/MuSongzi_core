package com.mszsupport.itf

import com.mszsupport.comment.BaseWrapBusiness

interface ITypeFactory {


    fun <B> createInstance(): B?



}