package com.musongzi.core.itf

interface IArguamentHodler<D> :ILifeObject{

    fun putArgmanet(d: D?);

    fun getArgmanet(): D?

    fun handlerArgment()


}