package com.mszsupport.itf.holder

interface IHolderArguments<D>  {

    fun putArguments(d: D?);

    fun getArguments(): D?

    fun handlerArguments()


}