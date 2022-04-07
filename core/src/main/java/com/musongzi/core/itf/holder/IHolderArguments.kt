package com.musongzi.core.itf.holder

interface IHolderArguments<D>  {

    fun putArguments(d: D?);

    fun getArguments(): D?

    fun handlerArguments()


}