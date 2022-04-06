package com.musongzi.core.itf.holder

import com.musongzi.core.itf.ILifeObject

interface IHodlerArguments<D> : ILifeObject {

    fun putArguments(d: D?);

    fun getArguments(): D?

    fun handlerArguments()


}